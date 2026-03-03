package inventorymanagement.purchase_order_service.controller;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;
import inventorymanagement.purchase_order_service.service.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller: PurchaseOrderController
 *
 * Description:
 * Exposes REST endpoints for managing Purchase Orders.
 * Maps HTTP requests (GET, POST, DELETE) to the Service layer methods.
 */
@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDto> createOrder(@RequestBody PurchaseOrderRequestDto request) {
        return new ResponseEntity<>(purchaseOrderService.createPurchaseOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok("Purchase Order deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> updateOrder(
            @PathVariable Integer id,
            @RequestBody PurchaseOrderRequestDto request) {
        return ResponseEntity.ok(purchaseOrderService.updatePurchaseOrder(id, request));
    }


    // only update the status without changing the details in the purchase order.
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderResponseDto> changeStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return ResponseEntity.ok(purchaseOrderService.updateOrderStatus(id, status));
    }
}