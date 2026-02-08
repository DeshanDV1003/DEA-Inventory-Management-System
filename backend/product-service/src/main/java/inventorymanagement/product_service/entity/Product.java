package inventorymanagement.product_service.entity;

import jakarta.persistence.*;
import java.lang.annotation.Target;

@Entity
@Table(name = "tblProdcut")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;
    private String productName;
    private String productDescription;
    private String productCategoryId;

    public Product() {
    }

    public Product(String productId, String productName, String productDescription, String productCategoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategoryId = productCategoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategoryId=" + productCategoryId +
                '}';
    }
}
