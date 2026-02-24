package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.WarehouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "warehouse-service", url = "${services.warehouse}")
public interface WarehouseClient {
    @GetMapping("/api/warehouses/{id}")
    WarehouseResponse getWarehouse(@PathVariable Long id);
}