/**
 * PurchaseOrderController
 *
 * The REST API entry point for the Purchase Order Service.
 *
 * Purpose:
 * - Maps HTTP Verbs (GET, POST, PUT, PATCH, DELETE) to Service methods.
 * - Handles @PathVariable and @RequestBody data extraction.
 *
 * Annotations:
 * - @RestController: Marks the class as a web controller returning JSON.
 * - @RequestMapping("/api/v1/purchase-orders"): Base path for all endpoints.
 * - @Valid: Triggers the validation rules defined in the DTOs.
 */

package inventorymanagement.purchase_order_service.controller;

import inventorymanagement.purchase_order_service.dto.OrderStatusUpdateDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;
import inventorymanagement.purchase_order_service.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    /**
     * Create a new Purchase Order
     * Endpoint: POST /api/v1/purchase-orders
     *
     * Purpose: Receives order data, validates it, and persists it to the database.
     * @param request The validated PurchaseOrderRequestDto containing header and items.
     * @return PurchaseOrderResponseDto with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDto> createOrder(@Valid @RequestBody PurchaseOrderRequestDto request) {
        // Calls the service layer to map DTO to Entity and save the order
        PurchaseOrderResponseDto createdOrder = purchaseOrderService.createPurchaseOrder(request);

        // Returns the created object along with the HTTP 201 Created status code
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Retrieve a specific Purchase Order by ID
     * Endpoint: GET /api/v1/purchase-orders/{id}
     *
     * Purpose: Fetches the full details of a single order using its primary key.
     * @param id The unique database ID of the Purchase Order.
     * @return PurchaseOrderResponseDto with a 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> getOrderById(@PathVariable Integer id) {
        // Requests the specific order from the service layer
        PurchaseOrderResponseDto order = purchaseOrderService.getPurchaseOrderById(id);

        // Returns the order details with an HTTP 200 OK status
        return ResponseEntity.ok(order);
    }

    /**
     * Retrieve all Purchase Orders
     * Endpoint: GET /api/v1/purchase-orders
     *
     * Purpose: Returns a list of every purchase order currently in the database.
     * @return List of PurchaseOrderResponseDto with a 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponseDto>> getAllOrders() {
        // Retrieves the full collection of orders via the service layer
        List<PurchaseOrderResponseDto> orders = purchaseOrderService.getAllPurchaseOrders();

        // Returns the list with an HTTP 200 OK status
        return ResponseEntity.ok(orders);
    }

    /**
     * Delete a Purchase Order
     * Endpoint: DELETE /api/v1/purchase-orders/{id}
     *
     * Purpose: Removes the order header and all its associated line items (cascade delete).
     * @param id The ID of the order to be removed.
     * @return A success message string with a 200 OK status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        // Triggers the deletion logic in the service layer
        purchaseOrderService.deletePurchaseOrder(id);

        // Returns a confirmation message to the client
        return ResponseEntity.ok("Purchase Order deleted successfully.");
    }

    /**
     * Update an entire Purchase Order
     * Endpoint: PUT /api/v1/purchase-orders/{id}
     *
     * Purpose: Performs a full update of the PO header and replaces the existing item list.
     * @param id The ID of the order to update.
     * @param request The new order data (validated).
     * @return The updated PurchaseOrderResponseDto with a 200 OK status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> updateOrder(
            @PathVariable Integer id,
            @Valid @RequestBody PurchaseOrderRequestDto request) {

        // Passes the ID and new data to the service for modification
        PurchaseOrderResponseDto updatedOrder = purchaseOrderService.updatePurchaseOrder(id, request);

        // Returns the updated record to the client
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Update only the Order Status
     * Endpoint: PATCH /api/v1/purchase-orders/{id}/status
     *
     * Purpose: Performs a partial update to change the lifecycle status (e.g., APPROVED, SHIPPED).
     * This avoids having to send the entire order body just to change one field.
     *
     * @param id The ID of the order.
     * @param body The DTO containing only the new status string.
     * @return The updated PurchaseOrderResponseDto with a 200 OK status.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderResponseDto> changeStatus(
            @PathVariable Integer id,
            @Valid @RequestBody OrderStatusUpdateDto body) {

        // Triggers the partial update logic in the service layer
        PurchaseOrderResponseDto updatedOrder = purchaseOrderService.updateOrderStatus(id, body.getStatus());

        // Returns the record showing the new status to the client
        return ResponseEntity.ok(updatedOrder);
    }
}