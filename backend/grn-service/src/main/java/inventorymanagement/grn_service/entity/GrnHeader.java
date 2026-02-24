package inventorymanagement.grn_service.entity;

import inventorymanagement.grn_service.enums.GrnStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grn_header")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GrnHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private Long departmentId;
    private Long warehouseId;
    private Long supplierId;

    @Column(nullable = false)
    private String poNumber;

    @Column(nullable = false, unique = true)
    private String grnNumber;

    @Column(precision = 18, scale = 2)
    private BigDecimal totalGrossAmount;

    @Column(precision = 18, scale = 2)
    private BigDecimal totalDiscountAmount;

    @Column(precision = 18, scale = 2)
    private BigDecimal totalNetAmount;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private GrnStatus status;

    private String createdBy;
    private LocalDateTime createdDate;

    private String modifiedBy;
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrnDetail> details = new ArrayList<>();
}
