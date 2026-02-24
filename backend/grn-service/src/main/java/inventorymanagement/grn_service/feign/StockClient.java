package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.StockAdjustRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stock-service", url = "${services.stock}")
public interface StockClient {

    // Adjust stock (increase/decrease)
    @PostMapping("/api/stocks/adjust")
    void adjust(@RequestBody StockAdjustRequest request);
}