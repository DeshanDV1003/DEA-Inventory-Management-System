package inventorymanagement.grn_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GrnItemResponse {
    private Long productId;
    private Integer requestedQty;
    private Integer receivedQty;
    private BigDecimal unitPrice;
    private BigDecimal grossAmount;
    private BigDecimal discountAmount;
    private BigDecimal netAmount;
}