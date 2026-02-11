package inventorymanagement.product_service.service;

import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
    public Product findProductById(Long productId) {
        return productRepository.findProductByProductId(productId);
    }

}
