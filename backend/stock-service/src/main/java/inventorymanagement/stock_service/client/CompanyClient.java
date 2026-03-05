package inventorymanagement.stock_service.client;

import inventorymanagement.stock_service.dto.CompanyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "company-service", url = "${services.company.url}")
public interface CompanyClient {
    @GetMapping("/api/companies/{companyId}")
    CompanyResponseDTO getCompanyById(@PathVariable("companyId") int companyId);
}
