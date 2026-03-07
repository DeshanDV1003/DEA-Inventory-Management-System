package inventorymanagement.stock_service.client;

import inventorymanagement.stock_service.dto.CompanyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "company-service", url = "${services.company.url}")
public interface CompanyClient {
    @GetMapping("/api/v1/companies/getcompany")
    CompanyResponseDTO getCompanyById(@RequestParam("id") int companyId);
}
