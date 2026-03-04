package inventorymanagement.stock_service.mapper;

import inventorymanagement.stock_service.dto.StockRequestDTO;
import inventorymanagement.stock_service.dto.StockResponseDTO;
import inventorymanagement.stock_service.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock toEntity(StockRequestDTO dto) {
        Stock stock = new Stock();
        stock.setCompanyId(dto.getCompanyId());
        stock.setWarehouseId(dto.getWarehouseId());
        stock.setProductId(dto.getProductId());
        stock.setQuantity(dto.getQuantity());
        stock.setMaxStockLevel(dto.getMaxStockLevel());
        stock.setMinStockLevel(dto.getMinStockLevel());
        stock.setReOrderLevel(dto.getReOrderLevel());
        return stock;
    }

    public StockResponseDTO toResponseDTO(Stock stock) {
        StockResponseDTO dto = new StockResponseDTO();
        dto.setStockId(stock.getStockId());
        dto.setCompanyId(stock.getCompanyId());
        dto.setWarehouseId(stock.getWarehouseId());
        dto.setProductId(stock.getProductId());
        dto.setQuantity(stock.getQuantity());
        dto.setMaxStockLevel(stock.getMaxStockLevel());
        dto.setMinStockLevel(stock.getMinStockLevel());
        dto.setReOrderLevel(stock.getReOrderLevel());
        dto.setCreatedBy(stock.getCreatedBy());
        dto.setCreatedDateTime(stock.getCreatedDateTime());
        dto.setUpdatedBy(stock.getUpdatedBy());
        dto.setUpdatedDateTime(stock.getUpdatedDateTime());
        return dto;
    }

    public void updateEntityFromDTO(StockRequestDTO dto, Stock stock) {
        stock.setQuantity(dto.getQuantity());
        stock.setMaxStockLevel(dto.getMaxStockLevel());
        stock.setMinStockLevel(dto.getMinStockLevel());
        stock.setReOrderLevel(dto.getReOrderLevel());
        stock.setUpdatedBy(dto.getUpdatedBy());
    }

}
