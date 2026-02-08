package inventorymanagement.product_service.repository;

import inventorymanagement.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> getProductById(Long id);
}
