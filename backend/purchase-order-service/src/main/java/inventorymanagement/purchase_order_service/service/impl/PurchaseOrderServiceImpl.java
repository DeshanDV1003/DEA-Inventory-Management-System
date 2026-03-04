/**
 * PurchaseOrderServiceImpl
 *
 * Contains the core business logic and orchestration for the service.
 *
 * Logic Workflow:
 * 1. Uses PurchaseOrderMapper to convert DTOs to Entities.
 * 2. Manages audit fields (system_user, timestamp) manually before persistence.
 * 3. Utilizes @Transactional to ensure that if saving an item fails, the header
 *    is not saved (Data Atomicity).
 * 4. Interacts with PurchaseOrderRepository for database communication.
 *
 * Key Annotations:
 * - @Service: Registers this class as a Spring-managed Bean.
 * - @Transactional: Ensures database integrity during multi-step operations.
 */
package inventorymanagement.purchase_order_service.service.impl;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;
import inventorymanagement.purchase_order_service.entity.PurchaseOrder;
import inventorymanagement.purchase_order_service.entity.PurchaseOrderDetail;
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


    @Override
    @Transactional
    public PurchaseOrderResponseDto updatePurchaseOrder(Integer id, PurchaseOrderRequestDto request) {
        // 1. Find the existing order
        PurchaseOrder existingOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + id));

        // 2. Update Header Fields
        existingOrder.setCompanyId(request.getCompanyId());
        existingOrder.setSupplierId(request.getSupplierId());
        existingOrder.setWarehouseId(request.getWarehouseId());
        existingOrder.setPoNumber(request.getPoNumber());

        // Update audit fields
        existingOrder.setUpdatedBy("system_user");
        existingOrder.setUpdatedDate(LocalDateTime.now());

        // 3. Handle Items (Simplest way: Clear old items and add new ones)
        // This works because of orphanRemoval = true in the Entity
        existingOrder.getDetails().clear();

        if (request.getItems() != null) {
            request.getItems().forEach(itemDto -> {
                PurchaseOrderDetail detail = new PurchaseOrderDetail();
                detail.setProductId(itemDto.getProductId());
                detail.setQuantity(itemDto.getQuantity());
                detail.setPurchaseOrder(existingOrder); // Link to parent
                detail.setCreatedBy(existingOrder.getCreatedBy()); // Keep original creator
                detail.setCreatedDate(existingOrder.getCreatedDate());
                detail.setUpdatedBy("system_user");
                detail.setUpdatedDate(LocalDateTime.now());

                existingOrder.getDetails().add(detail);
            });
        }

        // 4. Save and Return
        PurchaseOrder updatedOrder = purchaseOrderRepository.save(existingOrder);
        return PurchaseOrderMapper.mapToResponse(updatedOrder);
    }

    // Add to PurchaseOrderServiceImpl.java

    @Override
    @Transactional
    public PurchaseOrderResponseDto updateOrderStatus(Integer id, String status) {
        // 1. Find the order
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + id));

        // 2. Update only the status and audit fields
        order.setStatus(status);
        order.setUpdatedBy("system_user");
        order.setUpdatedDate(LocalDateTime.now());

        // 3. Save and return
        PurchaseOrder updatedOrder = purchaseOrderRepository.save(order);
        return PurchaseOrderMapper.mapToResponse(updatedOrder);
    }
}