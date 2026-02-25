package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.WarehouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
Feign client used to communicate with the Warehouse microservice

This client is responsible for validating whether a warehouse exists before creating or updating a GRN

Base URL is configured in application.yml:services.warehouse: http://localhost:8083
 */

@FeignClient(name = "warehouse-service", url = "${services.warehouse}")
public interface WarehouseClient {
    /*
    Retrieve warehouse details by warehouse ID
    @param id warehouse identifier
    @return WarehouseResponse containing warehouse details
     */
    @GetMapping("/api/warehouses/{id}")
    WarehouseResponse getWarehouse(@PathVariable Long id);
}