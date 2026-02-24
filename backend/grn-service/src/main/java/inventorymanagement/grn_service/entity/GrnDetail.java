package inventorymanagement.grn_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grn_detail")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GrnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grnNumber;

    private Long productId;

    private Integer requestedQty;
    private Integer receivedQty;

    @Column(precision = 18, scale = 2)
    private BigDecimal unitPrice;

    @Column(precision = 18, scale = 2)
    private BigDecimal grossAmount;

    @Column(precision = 18, scale = 2)
    private BigDecimal discountAmount;

    @Column(precision = 18, scale = 2)
    private BigDecimal netAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id", nullable = false)
    private GrnHeader header;

}