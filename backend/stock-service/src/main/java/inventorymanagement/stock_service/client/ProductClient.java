package inventorymanagement.stock_service.client;
import inventorymanagement.stock_service.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${services.product.url}")
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ProductResponseDTO getProductById(@PathVariable("productId") int productId);
}
