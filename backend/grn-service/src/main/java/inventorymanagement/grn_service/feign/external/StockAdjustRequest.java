package inventorymanagement.grn_service.feign.external;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StockAdjustRequest {
    private Long warehouseId;
    private Long productId;
    private Integer qty; // + for increase, - for decrease
    private String reference; // GRN number
}
