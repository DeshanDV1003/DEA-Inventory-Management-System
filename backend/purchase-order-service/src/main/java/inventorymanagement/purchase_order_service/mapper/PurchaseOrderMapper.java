/**
 * PurchaseOrderMapper
 *
 * A utility class for manual mapping between Entities and DTOs.
 *
 * Purpose:
 * - Handles the heavy lifting of copying values from one object to another.
 * - Ensures bi-directional links between Header and Details are set correctly.
 *
 * Methods:
 * - mapToEntity: Converts RequestDto to PurchaseOrder entity.
 * - mapToResponse: Converts PurchaseOrder entity to ResponseDto.
 *
 * Architecture:
 * - Since Lombok/MapStruct is not used, this class ensures mapping logic
 *   remains centralized and out of the Service layer.
 */
package inventorymanagement.purchase_order_service.mapper;

import inventorymanagement.purchase_order_service.dto.PurchaseOrderDetailDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderRequestDto;
import inventorymanagement.purchase_order_service.dto.PurchaseOrderResponseDto;
import inventorymanagement.purchase_order_service.entity.PurchaseOrder;
import inventorymanagement.purchase_order_service.entity.PurchaseOrderDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderMapper {
    /**
     * Maps a PurchaseOrderRequest DTO to a PurchaseOrder Entity.
     */
    public static PurchaseOrder mapToEntity(PurchaseOrderRequestDto request) {
        if (request == null) {
            return null;
        }

        PurchaseOrder order = new PurchaseOrder();
        order.setCompanyId(request.getCompanyId());
        order.setSupplierId(request.getSupplierId());
        order.setWarehouseId(request.getWarehouseId());
        order.setPoNumber(request.getPoNumber());

        // Setting default metadata
        order.setDate(LocalDateTime.now());
        order.setStatus("CREATED");

        // Map the list of items
        if (request.getItems() != null) {
            List<PurchaseOrderDetail> details = request.getItems().stream()
                    .map(itemDto -> mapToDetailEntity(itemDto, order))
                    .collect(Collectors.toList());
            order.setDetails(details);
        }

        return order;
    }

    /**
     * Maps a PurchaseOrder Entity to a PurchaseOrderResponse DTO.
     */
    public static PurchaseOrderResponseDto mapToResponse(PurchaseOrder order) {
        if (order == null) return null;

        PurchaseOrderResponseDto response = new PurchaseOrderResponseDto();
        response.setId(order.getId());
        response.setCompanyId(order.getCompanyId());
        response.setSupplierId(order.getSupplierId());
        response.setWarehouseId(order.getWarehouseId());
        response.setPoNumber(order.getPoNumber());
        response.setDate(order.getDate());
        response.setStatus(order.getStatus());

        // Header Audit Fields
        response.setCreatedBy(order.getCreatedBy());
        response.setCreatedDate(order.getCreatedDate());
        response.setUpdatedBy(order.getUpdatedBy());
        response.setUpdatedDate(order.getUpdatedDate());

        if (order.getDetails() != null) {
            List<PurchaseOrderDetailDto> itemDtos = order.getDetails().stream()
                    .map(detail -> {
                        PurchaseOrderDetailDto itemDto = new PurchaseOrderDetailDto();
                        itemDto.setProductId(detail.getProductId());
                        itemDto.setQuantity(detail.getQuantity());

                        // Item Audit Fields
                        itemDto.setCreatedBy(detail.getCreatedBy());
                        itemDto.setCreatedDate(detail.getCreatedDate());
                        itemDto.setUpdatedBy(detail.getUpdatedBy());
                        itemDto.setUpdatedDate(detail.getUpdatedDate());

                        return itemDto;
                    }).collect(Collectors.toList());
            response.setItems(itemDtos);
        }
        return response;
    }

    /**
     * Helper method to map a detail item DTO to an entity and link it to the parent order.
     */
    private static PurchaseOrderDetail mapToDetailEntity(PurchaseOrderDetailDto dto, PurchaseOrder order) {
        PurchaseOrderDetail detail = new PurchaseOrderDetail();
        detail.setProductId(dto.getProductId());
        detail.setQuantity(dto.getQuantity());
        // This line is crucial for JPA to link the detail back to the header
        detail.setPurchaseOrder(order);
        return detail;
    }
}
