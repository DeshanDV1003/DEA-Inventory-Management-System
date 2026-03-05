package inventorymanagement.stock_service.dto;

public class ProductResponseDTO {
    private int productId;
    private String productName;
    private int companyId;

    public ProductResponseDTO() {}

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
}