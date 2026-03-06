package inventorymanagement.product_service.controller;

import inventorymanagement.product_service.dto.AddProductRequestDto;
import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value="/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping(value = "addProduct")
    public String insert(@RequestBody AddProductRequestDto request) {
        productService.saveProduct(request);
        return "Product added successfully";
    }

    @DeleteMapping("deleteProduct/{id}")
    public String delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "The product with id: " + id + " has been deleted";
    }

    @PutMapping("updateProduct/{id}")
    public String update(@PathVariable Integer id, @RequestBody AddProductRequestDto request) {
        productService.updateProduct(id, request);
        return "Product updated successfully";
    }

    @GetMapping("getproduct")
    public Product getProduct(@RequestParam Integer id) {
        return productService.findProductById(id);
    }
}

