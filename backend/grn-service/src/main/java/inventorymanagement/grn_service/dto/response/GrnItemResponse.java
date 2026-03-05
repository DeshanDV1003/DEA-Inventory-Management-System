package inventorymanagement.grn_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

/* Response DTO representing a single item inside a GRN
This object is returned to the client when retrieving or filtering GRN records

It includes calculated monetary values such as:
Gross amount,
Discount amount,
Net amount
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnItemResponse {

    // Unique product identifier
    private Long productId;
    //Quantity requested in the purchase order
    private Integer requestedQty;
    //Quantity actually received in the warehouse
    private Integer receivedQty;
    //Price per unit of the product
    private BigDecimal unitPrice;
    //Total gross amount (unitPrice * receivedQty)
    private BigDecimal grossAmount;
    //Discount applied to this item
    private BigDecimal discountAmount;
    //Final amount after discount (grossAmount - discountAmount)
    private BigDecimal netAmount;
}