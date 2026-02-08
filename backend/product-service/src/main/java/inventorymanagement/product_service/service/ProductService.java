package inventorymanagement.product_service.service;

import inventorymanagement.product_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private inventorymanagement.product_service.repository.ProductRepository ProductRepository;

    public List<Product> getProducts() {
        return ProductRepository.findAll();
    }
    public Product insertProduct(Product Product) {
        return ProductRepository.save(Product);
    }
    public String deleteProduct(Long id) {
        ProductRepository.deleteById(id);
        return "Product with id: " + id + " was deleted";
    }
    public List<Product> getProductsById(Long id) {
        return ProductRepository.getProductById(id);
    }
}
