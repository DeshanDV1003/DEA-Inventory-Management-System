package inventorymanagement.grn_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/* Request DTO representing a single item inside a GRN

Each GRN can contain multiple items,
This class holds item level details such as:
product id,
requested quantity,
received quantity,
unit price,discount amount

Validation ensures required fields are provided end quantity values are not negative
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnItemRequest {

    // Unique identifier of the product
    @NotNull (message = "Product ID is required")
    private Long productId;

    // Quantity requested in the purchase order
    @NotNull (message = "Requested quantity is required")
    @Min(value = 0, message = "Requested quantity cannot be negative")
    private Integer requestedQty;

    //Quantity actually received in the warehouse
    @NotNull (message = "Received quantity is required")
    @Min(value = 0, message = "Received quantity cannot be negative")
    private Integer receivedQty;

    //Price per unit of the product
    @NotNull (message = "Unit price is required")
    private BigDecimal unitPrice;

    //Discount amount applied to this item and if null,it will be treated as 0 in calculations
    private BigDecimal discountAmount;
}
