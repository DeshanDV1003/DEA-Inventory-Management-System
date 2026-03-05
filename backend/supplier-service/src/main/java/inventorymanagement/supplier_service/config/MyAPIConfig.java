package inventorymanagement.supplier_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyAPIConfig {

    @Value("${services.product.url}")
    private String productServiceUrl;

    @Value("${services.purchase-order.url}")
    private String poServiceUrl;

    @Value("${services.grn.url}")
    private String grnServiceUrl;

    @Value("${services.company.url}")
    private String companyServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getProductServiceUrl() {
        return productServiceUrl;
    }

    public String getPoServiceUrl() {
        return poServiceUrl;
    }

    public String getGrnServiceUrl() {
        return grnServiceUrl;
    }

    public String getCompanyServiceUrl() {
        return companyServiceUrl;
    }
}
