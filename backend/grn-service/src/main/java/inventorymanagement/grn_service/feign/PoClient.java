package inventorymanagement.grn_service.feign;

import inventorymanagement.grn_service.feign.external.PoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "po-service", url = "${services.po}")
public interface PoClient {
    @GetMapping("/api/pos/{poNumber}")
    PoResponse getPo(@PathVariable String poNumber);
}