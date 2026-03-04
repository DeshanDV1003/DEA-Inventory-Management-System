package inventorymanagement.stock_service.service.impl;

import inventorymanagement.stock_service.constants.AppConstants;
import inventorymanagement.stock_service.dto.StockRequestDTO;
import inventorymanagement.stock_service.dto.StockResponseDTO;
import inventorymanagement.stock_service.exception.DuplicateStockException;
import inventorymanagement.stock_service.exception.ResourceNotFoundException;
import inventorymanagement.stock_service.model.Stock;
import inventorymanagement.stock_service.repository.StockRepository;
import inventorymanagement.stock_service.service.StockService;
import inventorymanagement.stock_service.mapper.StockMapper;
import inventorymanagement.stock_service.utill.StockValidationUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final StockValidationUtil validationUtil;

    public StockServiceImpl(StockRepository stockRepository,
                            StockMapper stockMapper,
                            StockValidationUtil validationUtil) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    @Transactional
    public StockResponseDTO createStock(StockRequestDTO requestDTO) {
        // Validate stock levels
        validationUtil.validateStockLevels(requestDTO);

        // Validate that company, warehouse, product exist via Feign
        validationUtil.validateCompanyExists(requestDTO.getCompanyId());
        validationUtil.validateWarehouseExists(requestDTO.getWarehouseId());
        validationUtil.validateProductExists(requestDTO.getProductId());

        // Enforce uniqueness
        if (stockRepository.existsByCompanyIdAndProductIdAndWarehouseId(
                requestDTO.getCompanyId(), requestDTO.getProductId(), requestDTO.getWarehouseId())) {
            throw new DuplicateStockException(AppConstants.STOCK_DUPLICATE);
        }

        Stock stock = stockMapper.toEntity(requestDTO);
        Stock saved = stockRepository.save(stock);
        return stockMapper.toResponseDTO(saved);
    }

    @Override
    public StockResponseDTO getStockById(int stockId) {
        Stock stock = findStockByIdOrThrow(stockId);
        return stockMapper.toResponseDTO(stock);
    }

    @Override
    public List<StockResponseDTO> getAllStocks() {
        return stockRepository.findAll()
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getStocksByCompany(int companyId) {
        return stockRepository.findByCompanyId(companyId)
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getStocksByWarehouse(int warehouseId) {
        return stockRepository.findByWarehouseId(warehouseId)
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getStocksByProduct(int productId) {
        return stockRepository.findByProductId(productId)
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getStocksByCompanyAndWarehouse(int companyId, int warehouseId) {
        return stockRepository.findByCompanyIdAndWarehouseId(companyId, warehouseId)
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponseDTO> getLowStocks(int companyId, int threshold) {
        return stockRepository.findByCompanyIdAndQuantityLessThanEqual(companyId, threshold)
                .stream().map(stockMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockResponseDTO updateStock(int stockId, StockRequestDTO requestDTO) {
        validationUtil.validateStockLevels(requestDTO);
        Stock stock = findStockByIdOrThrow(stockId);
        stockMapper.updateEntityFromDTO(requestDTO, stock);
        Stock updated = stockRepository.save(stock);
        return stockMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteStock(int stockId) {
        Stock stock = findStockByIdOrThrow(stockId);
        stockRepository.delete(stock);
    }

    private Stock findStockByIdOrThrow(int stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        AppConstants.STOCK_NOT_FOUND + stockId));
    }

}
