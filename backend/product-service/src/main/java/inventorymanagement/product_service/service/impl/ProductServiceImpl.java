package inventorymanagement.product_service.service.impl;

import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.repository.ProductRepository;
import inventorymanagement.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
    public Product findProductById(Integer id) {
        return productRepository.findProductById(id);
    }

}
