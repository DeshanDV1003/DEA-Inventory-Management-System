package inventorymanagement.purchase_order_service.dto;

public class PurchaseOrderDetailDto {
    private Integer productId;
    private Integer quantity;

    // Default Constructor
    public PurchaseOrderDetailDto() {
    }

    // Parameterized Constructor
    public PurchaseOrderDetailDto(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
