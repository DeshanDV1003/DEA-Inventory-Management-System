package inventorymanagement.asset_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "asset")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String modifiedBy;
    private LocalDate modifiedDate;

    @Column(name = "is_deleted")
    private boolean deleted = false; // For Soft Delete
}