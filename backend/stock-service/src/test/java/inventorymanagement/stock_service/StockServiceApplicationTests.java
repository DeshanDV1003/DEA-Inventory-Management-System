package inventorymanagement.stock_service;

import inventorymanagement.stock_service.client.CompanyClient;
import inventorymanagement.stock_service.client.ProductClient;
import inventorymanagement.stock_service.client.WarehouseClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class StockServiceApplicationTests {

	@MockBean
	private CompanyClient companyClient;

	@MockBean
	private WarehouseClient warehouseClient;

	@MockBean
	private ProductClient productClient;

	@Test
	void contextLoads() {
	}

}
