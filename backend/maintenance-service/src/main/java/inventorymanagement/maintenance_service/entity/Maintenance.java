package inventorymanagement.maintenance_service.entity;

import jakarta.persistence.*; // Imports JPA (Java Persistence API) (Hibernate) annotation @Entity, @Table, @Id, @GeneratedValue

import java.math.BigDecimal;
import java.time.LocalDateTime;
// Helps to connect java class to thr DB

@Entity // This class represent as a database table spring boot in,/ without it spring not create or map table
@Table(name = "maintenances") // Set a table name
public class Maintenance { // Maintenance is a model class that represents the table (1 class = 1 table)

    @Id // Primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Database automatically generate the ID
    private Integer id; // Primary key column (integer -object)

    // Create columns in table
    @Column(name = "maintenance_number", length = 20, nullable = false)
    private String maintenanceNumber;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name ="warehouse_id")
    private Integer warehouseId;

    @Column(name = "asset_id", nullable = false)
    private Integer assetId;

    private LocalDateTime date;

    @Column(precision = 15, scale = 2)
    private BigDecimal cost;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="status_id")
    private MaintenanceStatus status;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Maintenance() { // Empty/ Default Constructor
    }

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaintenanceNumber() {
        return maintenanceNumber;
    }

    public void setMaintenanceNumber(String maintenanceNumber) {
        this.maintenanceNumber = maintenanceNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", maintenanceNumber='" + maintenanceNumber + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                '}';
    }
}

