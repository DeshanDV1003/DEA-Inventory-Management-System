package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.StockAdjustRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
Feign client used to communicate with the Stock microservice

This client is responsible for adjusting stock quantities
when a GRN is:
Created (increase stock),
Updated (adjust difference),
Cancelled (reverse stock)

Base URL is configured via application.yml:services.stock: http://localhost:8082
 */

@FeignClient(name = "stock-service", url = "${services.stock}")
public interface StockClient {

    /*
     Adjust stock quantity for a specific product and warehouse (increase/decrease)
     @param request contains warehouseId, productId,quantity adjustment, and reference number
     */

    @PostMapping("/api/v1/stocks/adjust")
    void adjust(@RequestBody StockAdjustRequest request);
}