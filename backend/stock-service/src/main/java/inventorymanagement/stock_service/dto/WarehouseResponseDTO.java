package inventorymanagement.stock_service.dto;

public class WarehouseResponseDTO {
    private int warehouseId;
    private String warehouseName;
    private int companyId;

    public WarehouseResponseDTO() {}

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
}
