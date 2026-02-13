package inventorymanagement.product_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tblProduct")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private Long categoryId;

    public Product() {
    }

    public Product(Long productId, String productName, String productDescription, Long categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}