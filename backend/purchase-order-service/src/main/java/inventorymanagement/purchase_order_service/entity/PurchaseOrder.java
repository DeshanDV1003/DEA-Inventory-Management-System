package inventorymanagement.purchase_order_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Model: PurchaseOrder
 * Table: po_headers
 *
 * Description:
 * This model represents the header record for a Purchase Order. It stores
 * primary identification data, administrative links (company, supplier, warehouse),
 * and audit tracking fields (created/updated info).
 *
 * It maintains a One-to-Many relationship with PurchaseOrderDetail to link
 * specific items to this order header.
 */



@Entity
@Table(name = "po_headers")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @Column(name = "warehouse_id", nullable = false, length = 255)
    private String warehouseId;

    @Column(name = "po_number", nullable = false)
    private Integer poNumber;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderDetail> details;


    public PurchaseOrder() {
    }

    public PurchaseOrder(Integer id, Integer companyId, Integer supplierId, String warehouseId, Integer poNumber, LocalDateTime date, String status, String createdBy, LocalDateTime createdDate, String updatedBy, LocalDateTime updatedDate, List<PurchaseOrderDetail> details) {
        this.id = id;
        this.companyId = companyId;
        this.supplierId = supplierId;
        this.warehouseId = warehouseId;
        this.poNumber = poNumber;
        this.date = date;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(Integer poNumber) {
        this.poNumber = poNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<PurchaseOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseOrderDetail> details) {
        this.details = details;
    }


    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", supplierId=" + supplierId +
                ", warehouseId='" + warehouseId + '\'' +
                ", poNumber=" + poNumber +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", details=" + details +
                '}';
    }
}
