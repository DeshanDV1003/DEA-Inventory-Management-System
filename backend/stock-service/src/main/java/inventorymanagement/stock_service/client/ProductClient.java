package inventorymanagement.stock_service.client;
import inventorymanagement.stock_service.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${services.product.url}")
public interface ProductClient {

    @GetMapping("/api/v1/products/getproduct")
    ProductResponseDTO getProductById(@RequestParam("id") int productId);
}
