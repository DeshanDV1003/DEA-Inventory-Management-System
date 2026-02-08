package inventorymanagement.product_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tblProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProductId;
    private String ProductName;
    private String ProductDescription;
    private String CategoryId;

    public Product() {
    }

    public Product(int productId, String productName, String productDescription, String categoryId) {
        ProductId = productId;
        ProductName = productName;
        ProductDescription = productDescription;
        CategoryId = categoryId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
