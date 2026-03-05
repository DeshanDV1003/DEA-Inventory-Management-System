package inventorymanagement.stocktransfer.repository;

import inventorymanagement.stocktransfer.entity.StockTransferDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransferDetailRepository extends JpaRepository<StockTransferDetail, Long> {


    List<StockTransferDetail> findByHeader_TransferNo(String transferNo);
}