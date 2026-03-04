package inventorymanagement.stock_service.service;

import inventorymanagement.stock_service.dto.StockRequestDTO;
import inventorymanagement.stock_service.dto.StockResponseDTO;
import inventorymanagement.stock_service.model.Stock;

import java.util.List;

public interface StockService {
    StockResponseDTO createStock(StockRequestDTO requestDTO);

    StockResponseDTO getStockById(int stockId);

    List<StockResponseDTO> getAllStocks();

    List<StockResponseDTO> getStocksByCompany(int companyId);

    List<StockResponseDTO> getStocksByWarehouse(int warehouseId);

    List<StockResponseDTO> getStocksByProduct(int productId);

    List<StockResponseDTO> getStocksByCompanyAndWarehouse(int companyId, int warehouseId);

    List<StockResponseDTO> getLowStocks(int companyId, int threshold);

    StockResponseDTO updateStock(int stockId, StockRequestDTO requestDTO);

    void deleteStock(int stockId);

}
