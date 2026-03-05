package inventorymanagement.asset_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouse-service", url = "http://localhost:8085")
public interface WarehouseClient {
    @GetMapping("/api/v1/warehouses/{id}")
    Object getWarehouseById(@PathVariable("id") Long id);
}