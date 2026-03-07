package inventorymanagement.stock_service.client;

import inventorymanagement.stock_service.dto.WarehouseResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "warehouse-service", url = "${services.warehouse.url}")
public interface WarehouseClient {

    @GetMapping("/api/v1/warehouses/{warehouseId}")
    WarehouseResponseDTO getWarehouseById(@PathVariable("warehouseId") int warehouseId);
}
