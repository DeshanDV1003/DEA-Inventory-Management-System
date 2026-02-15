package inventorymanagement.stock_service.repository;

import inventorymanagement.stock_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDAO extends JpaRepository<Stock, Long> {
}
