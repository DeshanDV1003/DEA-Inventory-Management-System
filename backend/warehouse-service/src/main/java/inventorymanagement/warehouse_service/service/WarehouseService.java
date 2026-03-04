package inventorymanagement.warehouse_service.service;

import inventorymanagement.warehouse_service.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO);
    List<WarehouseDTO> getAllWarehouses();
    WarehouseDTO getWarehouseById(Long id);
    WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO);
    void deleteWarehouse(Long id);

    // cross-service helper methods
    Object getPurchaseOrdersByWarehouse(Long id);
    Object getStockTransfersByWarehouse(Long id);
    Object getGRNsByWarehouse(Long id);
    Object getStockByWarehouse(Long id);
    Object getAllCompanies();
}