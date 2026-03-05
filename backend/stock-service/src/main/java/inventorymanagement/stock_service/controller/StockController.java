package inventorymanagement.stock_service.controller;

import inventorymanagement.stock_service.constants.AppConstants;
import inventorymanagement.stock_service.dto.ApiResponseDTO;
import inventorymanagement.stock_service.dto.StockRequestDTO;
import inventorymanagement.stock_service.dto.StockResponseDTO;
import inventorymanagement.stock_service.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<StockResponseDTO>> createStock(
            @Valid @RequestBody StockRequestDTO requestDTO) {

        StockResponseDTO response = stockService.createStock(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDTO<>(true, AppConstants.STOCK_CREATED, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getAllStocks() {
        List<StockResponseDTO> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<ApiResponseDTO<StockResponseDTO>> getStockById(@PathVariable int stockId) {
        StockResponseDTO stock = stockService.getStockById(stockId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_FETCHED, stock));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getStocksByCompany(
            @PathVariable int companyId) {

        List<StockResponseDTO> stocks = stockService.getStocksByCompany(companyId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getStocksByWarehouse(
            @PathVariable int warehouseId) {

        List<StockResponseDTO> stocks = stockService.getStocksByWarehouse(warehouseId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getStocksByProduct(
            @PathVariable int productId) {

        List<StockResponseDTO> stocks = stockService.getStocksByProduct(productId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @GetMapping("/company/{companyId}/warehouse/{warehouseId}")
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getStocksByCompanyAndWarehouse(
            @PathVariable int companyId,
            @PathVariable int warehouseId) {

        List<StockResponseDTO> stocks = stockService.getStocksByCompanyAndWarehouse(companyId, warehouseId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @GetMapping("/company/{companyId}/low")
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getLowStocks(
            @PathVariable int companyId,
            @RequestParam(defaultValue = "10") int threshold) {

        List<StockResponseDTO> stocks = stockService.getLowStocks(companyId, threshold);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_LIST_FETCHED, stocks));
    }

    @PutMapping("/{stockId}")
    public ResponseEntity<ApiResponseDTO<StockResponseDTO>> updateStock(
            @PathVariable int stockId,
            @Valid @RequestBody StockRequestDTO requestDTO) {

        StockResponseDTO updated = stockService.updateStock(stockId, requestDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_UPDATED, updated));
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStock(@PathVariable int stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, AppConstants.STOCK_DELETED, null));
    }

}
