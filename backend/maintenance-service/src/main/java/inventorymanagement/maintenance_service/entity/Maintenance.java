/*
 * Maintenance Entity
 *
 * This class represents the "maintenances" table in the maintenance_db database.
 *
 * Purpose:
 * - Stores all maintenance records related to assets.
 * - Each record contains maintenance details such as cost, date, description,
 *   and references to company, warehouse, asset, and status.
 *
 * Database Mapping:
 * - @Entity tells JPA (Hibernate) that this class is mapped to a database table.
 * - @Table(name = "maintenances") specifies the exact table name.
 * - @Id defines the primary key.
 * - @GeneratedValue(strategy = GenerationType.IDENTITY) enables auto-increment ID.
 *
 * Microservice Architecture:
 * - companyId, warehouseId, and assetId are references to other microservices.
 * - Only IDs are stored (no direct entity relationship to other services).
 * - statusId refers to the maintenance_status table inside this service.
 *
 * Data Types:
 * - BigDecimal is used for cost to ensure precision for monetary values.
 * - LocalDateTime is used for date fields to store date and time.
 *
 * Audit Fields:
 * - createdBy and createdDate store record creation details.
 * - modifiedBy and modifiedDate store last update information.
 *
 * This entity follows JPA standards and supports clean microservice architecture design.
 */

package inventorymanagement.maintenance_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// This class represents a database table.
@Entity

@Table(name = "maintenances")
public class Maintenance {

    // This field is primary key
    @Id

    // Primary key is generated automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Maintenance unique number (like reference number)
    private String maintenanceNumber;

    // Foreign key reference to Company service (microservice ID only)
    private Integer companyId;

    // Foreign key reference to Warehouse service
    private Integer warehouseId;

    // Foreign key reference to Asset service
    private Integer assetId;

    // Date and time when maintenance happened
    private LocalDateTime date;

    // Cost of maintenance (BigDecimal is used for money values)
    private BigDecimal cost;

    // Description/details about the maintenance
    private String description;

    // Status ID (foreign key to maintenance_status table)
    //private Integer statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private MaintenanceStatus status;

    // User who created the record
    private String createdBy;

    // Date when record was created
    private LocalDateTime createdDate;

    // User who last modified the record
    private String modifiedBy;

    // Date when record was last updated
    private LocalDateTime modifiedDate;

    // Default constructor
    // For JPA - (Java Persistence API) Connect java object(classes)
    public Maintenance() {
    }


     //Parameterized constructor (Used to create object with all fields.)

    public Maintenance(Integer id, String maintenanceNumber, Integer companyId,
                       Integer warehouseId, Integer assetId,
                       LocalDateTime date, BigDecimal cost, String description,
                       MaintenanceStatus status, String createdBy, LocalDateTime createdDate,
                       String modifiedBy, LocalDateTime modifiedDate) {
        this.id = id;
        this.maintenanceNumber = maintenanceNumber;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.assetId = assetId;
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }


    //  =================== Getters and Setters   ===================
    // These methods allow other classes to access and modify private fields.
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}