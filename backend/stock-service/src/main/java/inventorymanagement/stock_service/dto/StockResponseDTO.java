package inventorymanagement.stock_service.dto;

import java.time.LocalDateTime;

public class StockResponseDTO {

    private int stockId;
    private int companyId;
    private int productId;
    private int warehouseId;
    private int quantity;
    private int maxStockLevel;
    private int minStockLevel;
    private int reOrderLevel;
    private String createdBy;
    private LocalDateTime createdDateTime;
    private String updatedBy;
    private LocalDateTime updatedDateTime;

    public StockResponseDTO() {
    }

    public StockResponseDTO(int stockId, int companyId, int productId, int warehouseId, int quantity, int maxStockLevel, int minStockLevel, int reOrderLevel, String createdBy, LocalDateTime createdDateTime, String updatedBy, LocalDateTime updatedDateTime) {
        this.stockId = stockId;
        this.companyId = companyId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.maxStockLevel = maxStockLevel;
        this.minStockLevel = minStockLevel;
        this.reOrderLevel = reOrderLevel;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    @Override
    public String toString() {
        return "StockResponseDTO{" +
                "stockId=" + stockId +
                ", companyId=" + companyId +
                ", productId=" + productId +
                ", warehouseId=" + warehouseId +
                ", quantity=" + quantity +
                ", maxStockLevel=" + maxStockLevel +
                ", minStockLevel=" + minStockLevel +
                ", reOrderLevel=" + reOrderLevel +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }
}
