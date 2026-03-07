package inventorymanagement.grn_service.service;

import inventorymanagement.grn_service.dto.request.CreateGrnRequest;
import inventorymanagement.grn_service.dto.request.GrnItemRequest;
import inventorymanagement.grn_service.dto.request.UpdateGrnRequest;
import inventorymanagement.grn_service.dto.response.GrnItemResponse;
import inventorymanagement.grn_service.dto.response.GrnResponse;
import inventorymanagement.grn_service.entity.GrnDetail;
import inventorymanagement.grn_service.entity.GrnHeader;
import inventorymanagement.grn_service.enums.GrnStatus;
import inventorymanagement.grn_service.exception.BadRequestException;
import inventorymanagement.grn_service.exception.NotFoundException;
import inventorymanagement.grn_service.feign.PoClient;
import inventorymanagement.grn_service.feign.StockClient;
import inventorymanagement.grn_service.feign.WarehouseClient;
import inventorymanagement.grn_service.feign.external.PoResponse;
import inventorymanagement.grn_service.feign.external.StockAdjustRequest;
import inventorymanagement.grn_service.feign.external.WarehouseResponse;
import inventorymanagement.grn_service.repository.GrnDetailRepository;
import inventorymanagement.grn_service.repository.GrnHeaderRepository;
import inventorymanagement.grn_service.util.GrnNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrnService {

    private final GrnHeaderRepository headerRepo;
    private final GrnDetailRepository detailRepo;

    private final PoClient poClient;
    private final WarehouseClient warehouseClient;
    private final StockClient stockClient;

    // ---------------- CREATE ----------------
    @Transactional
    public GrnResponse create(CreateGrnRequest req) {

        // 1) Validate Warehouse
        WarehouseResponse wh = safeGetWarehouse(req.getWarehouseId());
        if (wh == null) throw new BadRequestException("Warehouse not found: " + req.getWarehouseId());

        // 3) Generate GRN number
        String grnNumber;
        do {
            grnNumber = GrnNumberGenerator.generate();
        } while (headerRepo.existsByGrnNumber(grnNumber));

        final String finalGrnNumber = grnNumber;

        // 4) Calculate totals
        Totals totals = calculateTotals(req.getItems());

        // 5) Build header
        GrnHeader header = GrnHeader.builder()
                .companyId(req.getCompanyId())
                .warehouseId(req.getWarehouseId())
                .supplierId(req.getSupplierId())
                .poNumber(req.getPoNumber())
                .grnNumber(grnNumber)
                .totalGrossAmount(totals.totalGross)
                .totalDiscountAmount(totals.totalDiscount)
                .totalNetAmount(totals.totalNet)
                .date(req.getDate())
                .status(GrnStatus.COMPLETED) // your requirement says create should update stock => completed
                .createdBy(req.getCreatedBy())
                .createdDate(LocalDateTime.now())
                .build();

        // 6) Build details
        List<GrnDetail> details = req.getItems().stream()
                .map(item -> toDetail(item, header, finalGrnNumber))
                .toList();

        header.getDetails().addAll(details);

        // 7) Save
        GrnHeader saved = headerRepo.save(header);

        // 8) Update stock (increase receivedQty)
        for (GrnDetail d : details) {
            if (d.getReceivedQty() != null && d.getReceivedQty() > 0) {
                stockClient.adjust(StockAdjustRequest.builder()
                        .warehouseId(saved.getWarehouseId())
                        .productId(d.getProductId())
                        .qty(d.getReceivedQty()) // + increase
                        .reference(saved.getGrnNumber())
                        .build());
            }
        }

        return toResponse(saved);
    }

    // ---------------- GET ----------------
    public GrnResponse getByGrnNumber(String grnNumber) {
        GrnHeader header = headerRepo.findByGrnNumber(grnNumber)
                .orElseThrow(() -> new NotFoundException("GRN not found: " + grnNumber));
        return toResponse(header);
    }

    // ---------------- FILTER ----------------
    public List<GrnResponse> filter(Long companyId, Long warehouseId) {

        List<GrnHeader> list;

        if (companyId != null && warehouseId != null) {
            list = headerRepo.findByCompanyIdAndWarehouseId(companyId, warehouseId);
        } else if (companyId != null) {
            list = headerRepo.findByCompanyId(companyId);
        } else if (warehouseId != null) {
            list = headerRepo.findByWarehouseId(warehouseId);
        } else {
            list = headerRepo.findAll();
        }

        return list.stream().map(this::toResponse).toList();
    }

    // ---------------- EDIT ----------------
    @Transactional
    public GrnResponse update(String grnNumber, UpdateGrnRequest req) {
        GrnHeader header = headerRepo.findByGrnNumber(grnNumber)
                .orElseThrow(() -> new NotFoundException("GRN not found: " + grnNumber));

        if (header.getStatus() == GrnStatus.CANCELLED) {
            throw new BadRequestException("Cannot edit CANCELLED GRN: " + grnNumber);
        }

        // Validate warehouse again (if changed)
        WarehouseResponse wh = safeGetWarehouse(req.getWarehouseId());
        if (wh == null) throw new BadRequestException("Warehouse not found: " + req.getWarehouseId());

        // 1) Existing received quantities (by productId)
        Map<Long, Integer> oldReceivedByProduct = header.getDetails().stream()
                .collect(Collectors.toMap(GrnDetail::getProductId, d -> safeInt(d.getReceivedQty()), (a, b) -> b));

        // 2) New received quantities
        Map<Long, Integer> newReceivedByProduct = req.getItems().stream()
                .collect(Collectors.toMap(GrnItemRequest::getProductId, i -> safeInt(i.getReceivedQty()), (a, b) -> b));

        // 3) Adjust stock differences
        // diff = new - old
        Set<Long> allProducts = new HashSet<>();
        allProducts.addAll(oldReceivedByProduct.keySet());
        allProducts.addAll(newReceivedByProduct.keySet());

        for (Long productId : allProducts) {
            int oldQty = oldReceivedByProduct.getOrDefault(productId, 0);
            int newQty = newReceivedByProduct.getOrDefault(productId, 0);
            int diff = newQty - oldQty;

            if (diff != 0) {
                stockClient.adjust(StockAdjustRequest.builder()
                        .warehouseId(req.getWarehouseId())
                        .productId(productId)
                        .qty(diff) // + increase / - decrease
                        .reference(grnNumber)
                        .build());
            }
        }

        // 4) Update header fields
        Totals totals = calculateTotals(req.getItems());

        header.setCompanyId(req.getCompanyId());
        header.setWarehouseId(req.getWarehouseId());
        header.setSupplierId(req.getSupplierId());
        header.setDate(req.getDate());

        header.setTotalGrossAmount(totals.totalGross);
        header.setTotalDiscountAmount(totals.totalDiscount);
        header.setTotalNetAmount(totals.totalNet);

        header.setModifiedBy(req.getModifiedBy());
        header.setModifiedDate(LocalDateTime.now());

        // 5) Replace details (orphanRemoval = true will delete old details)
        header.getDetails().clear();
        List<GrnDetail> newDetails = req.getItems().stream().map(i -> toDetail(i, header, grnNumber)).toList();
        header.getDetails().addAll(newDetails);

        GrnHeader saved = headerRepo.save(header);
        return toResponse(saved);
    }

    // ---------------- CANCEL ----------------
    @Transactional
    public GrnResponse cancel(String grnNumber, String cancelledBy) {
        GrnHeader header = headerRepo.findByGrnNumber(grnNumber)
                .orElseThrow(() -> new NotFoundException("GRN not found: " + grnNumber));

        if (header.getStatus() == GrnStatus.CANCELLED) {
            throw new BadRequestException("GRN already cancelled: " + grnNumber);
        }

        // reverse stock: subtract receivedQty
        for (GrnDetail d : header.getDetails()) {
            int received = safeInt(d.getReceivedQty());
            if (received > 0) {
                stockClient.adjust(StockAdjustRequest.builder()
                        .warehouseId(header.getWarehouseId())
                        .productId(d.getProductId())
                        .qty(-received) // reverse
                        .reference(grnNumber)
                        .build());
            }
        }

        header.setStatus(GrnStatus.CANCELLED);
        header.setModifiedBy(cancelledBy);
        header.setModifiedDate(LocalDateTime.now());

        GrnHeader saved = headerRepo.save(header);
        return toResponse(saved);
    }

    // ---------------- Helpers ----------------

    private GrnDetail toDetail(GrnItemRequest item, GrnHeader header, String grnNumber) {
        BigDecimal unitPrice = nz(item.getUnitPrice());
        BigDecimal discount = nz(item.getDiscountAmount());

        BigDecimal gross = unitPrice.multiply(BigDecimal.valueOf(safeInt(item.getReceivedQty())));
        BigDecimal net = gross.subtract(discount);

        return GrnDetail.builder()
                .header(header)
                .grnNumber(grnNumber)
                .productId(item.getProductId())
                .requestedQty(safeIntObj(item.getRequestedQty()))
                .receivedQty(safeIntObj(item.getReceivedQty()))
                .unitPrice(unitPrice)
                .grossAmount(gross)
                .discountAmount(discount)
                .netAmount(net)
                .build();
    }

    private Totals calculateTotals(List<GrnItemRequest> items) {
        BigDecimal gross = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal net = BigDecimal.ZERO;

        for (GrnItemRequest i : items) {
            BigDecimal unitPrice = nz(i.getUnitPrice());
            BigDecimal dis = nz(i.getDiscountAmount());
            BigDecimal g = unitPrice.multiply(BigDecimal.valueOf(safeInt(i.getReceivedQty())));
            BigDecimal n = g.subtract(dis);

            gross = gross.add(g);
            discount = discount.add(dis);
            net = net.add(n);
        }
        return new Totals(gross, discount, net);
    }

    private GrnResponse toResponse(GrnHeader header) {
        List<GrnItemResponse> items = header.getDetails().stream()
                .map(d -> GrnItemResponse.builder()
                        .productId(d.getProductId())
                        .requestedQty(d.getRequestedQty())
                        .receivedQty(d.getReceivedQty())
                        .unitPrice(d.getUnitPrice())
                        .grossAmount(d.getGrossAmount())
                        .discountAmount(d.getDiscountAmount())
                        .netAmount(d.getNetAmount())
                        .build())
                .toList();

        return GrnResponse.builder()
                .grnNumber(header.getGrnNumber())
                .poNumber(header.getPoNumber())
                .companyId(header.getCompanyId())
                .warehouseId(header.getWarehouseId())
                .supplierId(header.getSupplierId())
                .date(header.getDate())
                .status(header.getStatus())
                .totalGrossAmount(header.getTotalGrossAmount())
                .totalDiscountAmount(header.getTotalDiscountAmount())
                .totalNetAmount(header.getTotalNetAmount())
                .items(items)
                .build();
    }

    private PoResponse safeGetPo(String poNumber) {
        try { return poClient.getPo(poNumber); }
        catch (Exception e) { return null; }
    }

    private WarehouseResponse safeGetWarehouse(Long id) {
        try { return warehouseClient.getWarehouse(id); }
        catch (Exception e) { return null; }
    }

    private int safeInt(Integer v) { return v == null ? 0 : v; }
    private Integer safeIntObj(Integer v) { return v == null ? 0 : v; }
    private BigDecimal nz(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }

    private record Totals(BigDecimal totalGross, BigDecimal totalDiscount, BigDecimal totalNet) {}
}