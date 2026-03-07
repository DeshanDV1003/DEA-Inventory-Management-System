package inventorymanagement.stock_service.dto;

public class StockAdjustRequestDTO {
    private Long warehouseId;
    private Long productId;
    private Integer qty;
    private String reference;

    public StockAdjustRequestDTO() {}

    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}
