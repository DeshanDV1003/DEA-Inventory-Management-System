package inventorymanagement.purchase_order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class PurchaseOrderRequestDto {

    @NotNull(message = "Company ID is required")
    private Integer companyId;

    @NotNull(message = "Supplier ID is required")
    private Integer supplierId;

    @NotBlank(message = "Warehouse ID is required")
    @Size(max = 255, message = "Warehouse ID cannot exceed 255 characters")
    private String warehouseId;

    @NotNull(message = "Purchase Order number is required")
    @Min(value = 1, message = "PO Number must be a positive integer")
    private Integer poNumber;

    @NotEmpty(message = "A purchase order must contain at least one product")
    @Valid // This is CRITICAL: it tells Spring to validate every item inside this list
    private List<PurchaseOrderDetailDto> items;


    public PurchaseOrderRequestDto() {
    }

    public PurchaseOrderRequestDto(Integer companyId, Integer supplierId, String warehouseId, Integer poNumber, List<PurchaseOrderDetailDto> items) {
        this.companyId = companyId;
        this.supplierId = supplierId;
        this.warehouseId = warehouseId;
        this.poNumber = poNumber;
        this.items = items;
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

    public List<PurchaseOrderDetailDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderDetailDto> items) {
        this.items = items;
    }
}
