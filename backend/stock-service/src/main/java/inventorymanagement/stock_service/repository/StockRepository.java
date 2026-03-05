package inventorymanagement.stock_service.repository;

import inventorymanagement.stock_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    // Check for uniqueness before create
    boolean existsByCompanyIdAndProductIdAndWarehouseId(int companyId, int productId, int warehouseId);

    // Find a specific stock record by its composite business key
    Optional<Stock> findByCompanyIdAndProductIdAndWarehouseId(int companyId, int productId, int warehouseId);

    // Get all stocks for a company
    List<Stock> findByCompanyId(int companyId);

    // Get all stocks in a specific warehouse
    List<Stock> findByWarehouseId(int warehouseId);

    // Get all stocks for a specific product across all warehouses
    List<Stock> findByProductId(int productId);

    // Get all stocks for a company in a specific warehouse
    List<Stock> findByCompanyIdAndWarehouseId(int companyId, int warehouseId);

    // Get stocks below reorder level (for alerts)
    List<Stock> findByCompanyIdAndQuantityLessThanEqual(int companyId, int reOrderLevel);
}
