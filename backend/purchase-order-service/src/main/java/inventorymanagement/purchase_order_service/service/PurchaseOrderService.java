package inventorymanagement.purchase_order_service.service;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrderResponseDto createPurchaseOrder(PurchaseOrderRequestDto request);

    PurchaseOrderResponseDto getPurchaseOrderById(Integer id);

    List<PurchaseOrderResponseDto> getAllPurchaseOrders();

    void deletePurchaseOrder(Integer id);
}
