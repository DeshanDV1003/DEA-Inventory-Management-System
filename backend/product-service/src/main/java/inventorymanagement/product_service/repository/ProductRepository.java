package inventorymanagement.product_service.repository;

import inventorymanagement.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findProductByProductId(Long productId);
}
