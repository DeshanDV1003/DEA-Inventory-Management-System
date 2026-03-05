package inventorymanagement.grn_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/* Entity representing item-level details of a GRN
Each GRN can contain multiple detail records

This table stores product-level information including:
product id,
requested quantity,
received quantity,
pricing details

Relationship:Many GrnDetail records belong to one GrnHeader
 */

@Entity
@Table(name = "grn_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetail {

    //Primary key of GRN detail record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //GRN number reference
    private String grnNumber;

    //Product identifier
    private Long productId;

    //Quantity requested in Purchase Order
    private Integer requestedQty;
    //Quantity actually received
    private Integer receivedQty;

    //Unit price of the product
    @Column(precision = 18, scale = 2)
    private BigDecimal unitPrice;

    //Total gross amount (unitPrice × receivedQty)
    @Column(precision = 18, scale = 2)
    private BigDecimal grossAmount;

    //Discount amount applied to this item
    @Column(precision = 18, scale = 2)
    private BigDecimal discountAmount;

    //Net amount after discount (gross - discount)
    @Column(precision = 18, scale = 2)
    private BigDecimal netAmount;

    //Many-to-One relationship with GRN Header, each detail belongs to one GRN header
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id", nullable = false)
    private GrnHeader header;

}