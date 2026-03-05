package inventorymanagement.stocktransfer.service;

import inventorymanagement.stocktransfer.dto.CreateTransferRequest;
import inventorymanagement.stocktransfer.dto.TransferItemRequest;
import inventorymanagement.stocktransfer.entity.StockTransferDetail;
import inventorymanagement.stocktransfer.entity.StockTransferHeader;
import inventorymanagement.stocktransfer.repository.StockTransferDetailRepository;
import inventorymanagement.stocktransfer.repository.StockTransferHeaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * StockTransferService
 * -------------------
 * Business logic for Stock Transfer module.
 *
 * Responsibilities:
 * 1) Create stock transfer (Header + Detail items)
 * 2) Fetch transfers (list, by transferNo)
 * 3) Fetch transfer detail lines by transferNo
 *
 * NOTE:
 * - Controller should only call these methods.
 * - Repository should only handle database operations.
 */
@Service
public class StockTransferService {

    private static final Logger log = LoggerFactory.getLogger(StockTransferService.class);

    // Keep constants to avoid typing raw strings many times
    private static final String STATUS_PENDING = "PENDING";
    private static final int TRANSFER_NO_SUFFIX_LENGTH = 5;

    private final StockTransferHeaderRepository headerRepo;
    private final StockTransferDetailRepository detailRepo;

    public StockTransferService(StockTransferHeaderRepository headerRepo,
                                StockTransferDetailRepository detailRepo) {
        this.headerRepo = headerRepo;
        this.detailRepo = detailRepo;
    }

    /**
     * Create a new stock transfer with header + detail lines.
     *
     * Workflow:
     * 1) Validate request
     * 2) Create & save header
     * 3) Create & save detail items
     * 4) Return the saved header
     */
    @Transactional
    public StockTransferHeader createTransfer(CreateTransferRequest request) {

        // 1) Validate input (basic validations only)
        validateCreateRequest(request);

        // 2) Build header entity
        StockTransferHeader header = buildHeader(request);

        // 3) Save header first (we need header ID for details)
        header = headerRepo.save(header);

        // 4) Build & save detail lines
        List<StockTransferDetail> details = buildDetails(header, request.getItems());
        detailRepo.saveAll(details);

        log.info("Stock transfer created: transferNo={}, fromWarehouse={}, toWarehouse={}, items={}",
                header.getTransferNo(),
                header.getFromWarehouseId(),
                header.getToWarehouseId(),
                details.size()
        );

        // 5) Return header (optional: re-fetch by transferNo)
        return headerRepo.findByTransferNo(header.getTransferNo()).orElse(header);
    }

    /**
     * Return all transfer headers.
     * Used by UI transfer list.
     */
    public List<StockTransferHeader> getAllHeaders() {
        return headerRepo.findAll();
    }

    /**
     * Get a transfer header by transfer number.
     * Throws error if not found.
     */
    public StockTransferHeader getByTransferNo(String transferNo) {
        if (transferNo == null || transferNo.trim().isEmpty()) {
            throw new IllegalArgumentException("transferNo is required");
        }

        return headerRepo.findByTransferNo(transferNo)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found: " + transferNo));
    }

    /**
     * Get detail lines by transfer number.
     * Note: detail does not store transferNo; it is linked through header relation.
     */
    public List<StockTransferDetail> getDetailsByTransferNo(String transferNo) {
        if (transferNo == null || transferNo.trim().isEmpty()) {
            throw new IllegalArgumentException("transferNo is required");
        }
        return detailRepo.findByHeader_TransferNo(transferNo);
    }

    // -----------------------------
    // Helper methods (private)
    // -----------------------------

    /**
     * Basic validation for CreateTransferRequest.
     * (No external service validation here - only local validation.)
     */
    private void validateCreateRequest(CreateTransferRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        if (request.getFromWarehouseId() == null || request.getToWarehouseId() == null) {
            throw new IllegalArgumentException("Warehouse IDs are required");
        }

        if (request.getFromWarehouseId().equals(request.getToWarehouseId())) {
            throw new IllegalArgumentException("From warehouse and To warehouse cannot be same");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("At least 1 item is required");
        }

        // Validate each item
        for (TransferItemRequest item : request.getItems()) {
            if (item.getProductId() == null) {
                throw new IllegalArgumentException("Item productId is required");
            }
            if (item.getQty() == null || item.getQty() <= 0) {
                throw new IllegalArgumentException("Item qty must be greater than 0");
            }
        }
    }

    /**
     * Creates and fills StockTransferHeader entity from request.
     */
    private StockTransferHeader buildHeader(CreateTransferRequest request) {
        StockTransferHeader header = new StockTransferHeader();
        header.setTransferNo(generateTransferNo());
        header.setTransferDate(LocalDateTime.now());
        header.setFromWarehouseId(request.getFromWarehouseId());
        header.setToWarehouseId(request.getToWarehouseId());
        header.setStatus(STATUS_PENDING);
        header.setRemark(request.getRemark());
        header.setCreatedBy(request.getCreatedBy());
        header.setCreatedDate(LocalDateTime.now());
        return header;
    }

    /**
     * Create list of detail entities for given header.
     * Each detail is linked to header using header reference.
     */
    private List<StockTransferDetail> buildDetails(StockTransferHeader header, List<TransferItemRequest> items) {
        List<StockTransferDetail> details = new ArrayList<>();

        for (TransferItemRequest item : items) {
            StockTransferDetail detail = new StockTransferDetail();
            detail.setHeader(header);

            detail.setProductId(item.getProductId());
            detail.setRequestedQty(item.getQty());

            // For now, transferQty is set same as requestedQty
            detail.setTransferQty(item.getQty());

            // Initially nothing received
            detail.setReceivedQty(0);

            detail.setStatus(STATUS_PENDING);
            detail.setRemark(null);

            details.add(detail);
        }

        return details;
    }

    /**
     * Generates transfer number in format: TR-xxxxx
     */
    private String generateTransferNo() {
        String suffix = UUID.randomUUID().toString().replace("-", "")
                .substring(0, TRANSFER_NO_SUFFIX_LENGTH)
                .toUpperCase();
        return "TR-" + suffix;
    }
}