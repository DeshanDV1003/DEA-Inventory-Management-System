package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.PoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
/*
Feign client used to communicate with the Purchase Order (PO) microservice

This client is responsible for validating whether a purchase order exists before creating a GRN
Base URL is configured via application.yml:services.po: http://localhost:8081
 */

@FeignClient(name = "po-service", url = "${services.po}")
public interface PoClient {

    /*
    Retrieve Purchase Order details by PO number,
    @param poNumber unique purchase order number
    @return PoResponse containing PO details
     */
    @GetMapping("/api/pos/{poNumber}")
    PoResponse getPo(@PathVariable String poNumber);
}