package inventorymanagement.purchase_order_service.service.impl;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;
import inventorymanagement.purchase_order_service.entity.PurchaseOrder;
import inventorymanagement.purchase_order_service.mapper.PurchaseOrderMapper;
import inventorymanagement.purchase_order_service.repository.PurchaseOrderRepository;
import inventorymanagement.purchase_order_service.service.PurchaseOrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    // Constructor injection (Best practice, replaces @Autowired)
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    @Transactional // Ensures atomicity: if one item fails, the whole order rolls back
    public PurchaseOrderResponseDto createPurchaseOrder(PurchaseOrderRequestDto request) {
        // 1. Convert DTO to Entity via Mapper
        PurchaseOrder order = PurchaseOrderMapper.mapToEntity(request);

        // 2. Business Logic: Set Audit Fields
        // In a real app, 'system' would be replaced by the logged-in username from Spring Security
        String currentUser = "system_user";
        LocalDateTime now = LocalDateTime.now();

        order.setCreatedBy(currentUser);
        order.setCreatedDate(now);
        order.setUpdatedBy(currentUser);
        order.setUpdatedDate(now);

        // Set audit fields for each detail item too
        if (order.getDetails() != null) {
            order.getDetails().forEach(detail -> {
                detail.setCreatedBy(currentUser);
                detail.setCreatedDate(now);
                detail.setUpdatedBy(currentUser);
                detail.setUpdatedDate(now);
            });
        }

        // 3. Save to Database (Cascade takes care of the details list)
        PurchaseOrder savedOrder = purchaseOrderRepository.save(order);

        // 4. Return the Response DTO
        return PurchaseOrderMapper.mapToResponse(savedOrder);
    }

    @Override
    public PurchaseOrderResponseDto getPurchaseOrderById(Integer id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + id));

        return PurchaseOrderMapper.mapToResponse(order);
    }

    @Override
    public List<PurchaseOrderResponseDto> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(PurchaseOrderMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePurchaseOrder(Integer id) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Purchase Order not found with ID: " + id);
        }
        purchaseOrderRepository.deleteById(id);
    }
}