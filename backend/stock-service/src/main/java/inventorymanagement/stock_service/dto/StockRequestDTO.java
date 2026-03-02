package inventorymanagement.stock_service.dto;

public class StockRequestDTO {
    private int stockId;
    private int companyId;
    private int warehouseId;
    private int quantity;
    private int maxStockLevel;
    private int minStockLevel;
    private int reOrderLevel;

    public StockRequestDTO(int stockId, int companyId, int warehouseId, int quantity, int maxStockLevel, int minStockLevel, int reOrderLevel) {
        this.stockId = stockId;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.maxStockLevel = maxStockLevel;
        this.minStockLevel = minStockLevel;
        this.reOrderLevel = reOrderLevel;
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

    @Override
    public String toString() {
        return "StockRequestDTO{" +
                "stockId=" + stockId +
                ", companyId=" + companyId +
                ", warehouseId=" + warehouseId +
                ", quantity=" + quantity +
                ", maxStockLevel=" + maxStockLevel +
                ", minStockLevel=" + minStockLevel +
                ", reOrderLevel=" + reOrderLevel +
                '}';
    }
}
