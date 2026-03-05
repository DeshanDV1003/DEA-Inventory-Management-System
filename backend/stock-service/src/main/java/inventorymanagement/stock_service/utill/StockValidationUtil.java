package inventorymanagement.stock_service.utill;

import feign.FeignException;
import inventorymanagement.stock_service.client.CompanyClient;
import inventorymanagement.stock_service.client.ProductClient;
import inventorymanagement.stock_service.client.WarehouseClient;
import inventorymanagement.stock_service.constants.AppConstants;
import inventorymanagement.stock_service.dto.StockRequestDTO;
import inventorymanagement.stock_service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
    public class StockValidationUtil {

        private final CompanyClient companyClient;
        private final WarehouseClient warehouseClient;
        private final ProductClient productClient;

        public StockValidationUtil(CompanyClient companyClient,
                                   WarehouseClient warehouseClient,
                                   ProductClient productClient) {
            this.companyClient = companyClient;
            this.warehouseClient = warehouseClient;
            this.productClient = productClient;
        }

        public void validateStockLevels(StockRequestDTO dto) {
            if (dto.getMinStockLevel() >= dto.getMaxStockLevel() ||
                    dto.getReOrderLevel() <= dto.getMinStockLevel() ||
                    dto.getReOrderLevel() >= dto.getMaxStockLevel()) {
                throw new IllegalArgumentException(AppConstants.INVALID_STOCK_LEVELS);
            }
        }

        public void validateCompanyExists(int companyId) {
            try {
                companyClient.getCompanyById(companyId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException(AppConstants.COMPANY_NOT_FOUND + companyId);
            }
        }

        public void validateWarehouseExists(int warehouseId) {
            try {
                warehouseClient.getWarehouseById(warehouseId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException(AppConstants.WAREHOUSE_NOT_FOUND + warehouseId);
            }
        }

        public void validateProductExists(int productId) {
            try {
                productClient.getProductById(productId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + productId);
            }
        }
}
