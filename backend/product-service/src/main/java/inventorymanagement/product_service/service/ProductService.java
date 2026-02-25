package inventorymanagement.product_service.service;

import inventorymanagement.product_service.dto.AddProductRequestDto;
import inventorymanagement.product_service.entity.Product;

import java.util.List;

public interface ProductService {
    public List<Product> findAllProducts();
    public void saveProduct(AddProductRequestDto request);
    public void deleteProduct(Integer id);
    public Product findProductById(Integer id);
}
