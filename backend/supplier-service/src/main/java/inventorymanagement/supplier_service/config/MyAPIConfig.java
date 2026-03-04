package inventorymanagement.supplier_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAPIConfig {

    @Value("${services.product.url:http://localhost:8081}")
    private String productServiceUrl;

    @Value("${services.purchase-order.url:http://localhost:8088}")
    private String poServiceUrl;

    @Value("${services.grn.url:http://localhost:8089}")
    private String grnServiceUrl;

    @Value("${services.company.url:http://localhost:8082}")
    private String companyServiceUrl;

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