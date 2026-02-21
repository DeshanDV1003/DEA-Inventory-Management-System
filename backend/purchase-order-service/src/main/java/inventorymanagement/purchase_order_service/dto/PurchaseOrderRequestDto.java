package inventorymanagement.purchase_order_service.dto;

import java.util.List;

public class PurchaseOrderRequestDto {
    private Integer companyId;
    private Integer supplierId;
    private String warehouseId;
    private Integer poNumber;
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
