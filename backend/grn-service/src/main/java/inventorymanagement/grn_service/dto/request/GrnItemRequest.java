package inventorymanagement.grn_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GrnItemRequest {

    @NotNull
    private Long productId;

    @NotNull @Min(0)
    private Integer requestedQty;

    @NotNull @Min(0)
    private Integer receivedQty;

    @NotNull
    private BigDecimal unitPrice;

    private BigDecimal discountAmount; // can be null => treated as 0
}
