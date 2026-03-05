package inventorymanagement.stock_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StockRequestDTO {
    @NotNull(message = "Company ID is required")
    private int companyId;

    @NotNull(message = "Warehouse ID is required")
    private int warehouseId;

    @NotNull(message = "Product ID is required")
    private int productId;

    @Min(value = 0, message = "Quantity cannot be negative" )
    private int quantity;
    private int maxStockLevel;
    private int minStockLevel;
    private int reOrderLevel;
    private String createdBy;
    private String updatedBy;

    public StockRequestDTO() {
    }

    public StockRequestDTO(int companyId, int warehouseId, int productId, int quantity, int maxStockLevel, int minStockLevel, int reOrderLevel, String createdBy, String updatedBy) {

        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
        this.maxStockLevel = maxStockLevel;
        this.minStockLevel = minStockLevel;
        this.reOrderLevel = reOrderLevel;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(int maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public int getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(int reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
