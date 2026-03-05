/**
 * PurchaseOrderResponseDto
 *
 * This DTO is used to return complete data to the client after an operation.
 *
 * Purpose:
 * - Returns "every single detail" related to a Purchase Order, including IDs and audit data.
 *
 * Included Fields:
 * - All header fields (ID, Company, Supplier, Warehouse, PO Number, Status).
 * - All audit fields (CreatedBy, CreatedDate, UpdatedBy, UpdatedDate).
 * - A nested list of PurchaseOrderDetailDto objects.
 *
 * Architecture:
 * - Protects the Entity from being serialized directly (prevents infinite recursion).
 * - Ensures the Frontend receives a clean, structured JSON object.
 */
package inventorymanagement.purchase_order_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderResponseDto {
    private Integer id;
    private Integer companyId;
    private Integer supplierId;
    private String warehouseId;
    private Integer poNumber;
    private LocalDateTime date;
    private String status;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private List<PurchaseOrderDetailDto> items;

    public PurchaseOrderResponseDto() {}

    // Getters and Setters for ALL fields
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCompanyId() { return companyId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }

    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }

    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }

    public Integer getPoNumber() { return poNumber; }
    public void setPoNumber(Integer poNumber) { this.poNumber = poNumber; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    public List<PurchaseOrderDetailDto> getItems() { return items; }
    public void setItems(List<PurchaseOrderDetailDto> items) { this.items = items; }
}