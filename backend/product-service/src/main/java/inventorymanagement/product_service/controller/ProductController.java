package inventorymanagement.product_service.controller;

import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value="api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping(value ="products")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }
    @PostMapping(value = "addProduct")
    public String insert(@RequestBody Product product) {
        productService.saveProduct(product);
        return "Product added successfully";
    }
    @DeleteMapping("deleteProduct")
    public String delete(@RequestBody Long id) {
        productService.deleteProduct(id);
        return "The product with id: " + id + " has been deleted";
    }
    @GetMapping("getproduct")
    public Product getProduct(Long id) {
        return productService.findProductById(id);
    }
}
