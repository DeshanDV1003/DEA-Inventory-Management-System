package inventorymanagement.asset_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "asset")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String assetTag;

    private LocalDate purchaseDate;
    private String warranty;
    private String status;

    // Reference IDs for organizational hierarchy
    private Long companyId;
    private Long departmentId;
    private Long warehouseId;

    // Audit fields
    private String createdBy;
    private LocalDate createdDate;

    private boolean isDeleted = false; // For Soft Delete
}