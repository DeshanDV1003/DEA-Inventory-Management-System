/**
 * PurchaseOrderService Interface
 *
 * Defines the business contract for the Purchase Order microservice.
 *
 * Methods:
 * - createPurchaseOrder: Logic to save a new PO and its items.
 * - getPurchaseOrderById: Logic to retrieve a single PO.
 * - getAllPurchaseOrders: Logic to retrieve all records.
 * - updatePurchaseOrder: Logic for full record modification.
 * - updateOrderStatus: Logic for partial update (Patching status only).
 * - deletePurchaseOrder: Logic to remove an order and its orphans.
 */
package inventorymanagement.purchase_order_service.service;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrderResponseDto createPurchaseOrder(PurchaseOrderRequestDto request);

    PurchaseOrderResponseDto getPurchaseOrderById(Integer id);

    List<PurchaseOrderResponseDto> getAllPurchaseOrders();

    void deletePurchaseOrder(Integer id);

    PurchaseOrderResponseDto updatePurchaseOrder(Integer id, PurchaseOrderRequestDto request);

    // to update only the status of a purchase order
    PurchaseOrderResponseDto updateOrderStatus(Integer id, String status);
}
