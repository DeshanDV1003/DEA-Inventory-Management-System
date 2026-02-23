package inventorymanagement.product_service.service;

import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.service.impl.ProductServiceImpl;

import java.util.List;

public interface ProductService {
    public List<Product> findAllProducts();
    public void saveProduct(Product product);
    public void deleteProduct(Integer id);
    public Product findProductById(Integer id);
}
