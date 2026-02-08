package inventorymanagement.product_service.controller;

import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/Product/v1")
public class ProductController {
    @Autowired
    private ProductService ProductService;

    @GetMapping
    public List<Product> getProducts() {
        return ProductService.getProducts();
    }

    @PostMapping(value = "insert")
    public Product createProduct(@RequestBody Product Product) {
        return ProductService.insertProduct(Product);
    }
    @PostMapping(value = "delete")
    public String deleteProduct(@RequestBody Long id) {
        return ProductService.deleteProduct(id);
    }
    @GetMapping(value = "getProduct")
    public List<Product> getProductsById(Long id) {
        return ProductService.getProductsById(id);
    }
}
