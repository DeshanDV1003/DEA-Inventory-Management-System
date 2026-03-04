package inventorymanagement.warehouse_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAPIConfig {

    @Value("${services.purchase-order.url:http://localhost:8088}")
    private String poServiceUrl;

    @Value("${services.stock-transfer.url:http://localhost:8085}")
    private String stockTransferServiceUrl;

    @Value("${services.grn.url:http://localhost:8089}")
    private String grnServiceUrl;

    @Value("${services.stock.url:http://localhost:8090}")
    private String stockServiceUrl;

    @Value("${services.company.url:http://localhost:8082}")
    private String companyServiceUrl;

    public String getPoServiceUrl() {
        return poServiceUrl;
    }

    public String getStockTransferServiceUrl() {
        return stockTransferServiceUrl;
    }

    public String getGrnServiceUrl() {
        return grnServiceUrl;
    }

    public String getStockServiceUrl() {
        return stockServiceUrl;
    }

    public String getCompanyServiceUrl() {
        return companyServiceUrl;
    }
}
