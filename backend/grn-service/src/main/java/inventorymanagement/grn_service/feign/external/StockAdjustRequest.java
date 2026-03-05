package inventorymanagement.grn_service.feign.external;

import lombok.*;

/*DTO used to send stock adjustment requests to the Stock microservice via Feign client

This is used when:
Creating a GRN (increase stock),
Updating a GRN (adjust difference),
Cancelling a GRN (reverse stock)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustRequest {
    //Warehouse where stock is stored
    private Long warehouseId;
    //Product identifier
    private Long productId;
    //Quantity to adjust (positive value- increase stock and negative value-decrease stock
    private Integer qty;
    //Reference number for tracking, usually the GRN number
    private String reference;
}
