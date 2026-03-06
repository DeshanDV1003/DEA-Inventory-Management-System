package inventorymanagement.product_service.service.impl;

import inventorymanagement.product_service.dto.AddProductRequestDto;
import inventorymanagement.product_service.entity.Product;
import inventorymanagement.product_service.repository.ProductRepository;
import inventorymanagement.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(AddProductRequestDto request) {
        Product product = new Product();
        product.setWarehouseId(request.getWarehouseId());
        product.setCompanyId(request.getCompanyId());
        product.setSupplierId(request.getSupplierId());
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setImgPath(request.getImgPath());
        product.setStatus(request.getStatus());
        product.setCreatedBy(request.getCreatedBy());
        product.setCreatedDate(LocalDateTime.now());
        product.setModifiedBy(request.getModifiedBy());
        product.setModifiedDate(LocalDateTime.now());

        productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Integer id, AddProductRequestDto request) {
        Product product = productRepository.findProductById(id);
        product.setWarehouseId(request.getWarehouseId());
        product.setCompanyId(request.getCompanyId());
        product.setSupplierId(request.getSupplierId());
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setImgPath(request.getImgPath());
        product.setStatus(request.getStatus());
        product.setModifiedBy(request.getModifiedBy());
        product.setModifiedDate(LocalDateTime.now());
        productRepository.save(product);
    }

    public Product findProductById(Integer id) {
        return productRepository.findProductById(id);
    }
}
