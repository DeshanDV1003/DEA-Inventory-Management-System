package inventorymanagement.grn_service.repository;

import inventorymanagement.grn_service.entity.GrnDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrnDetailRepository extends JpaRepository<GrnDetail, Long> {
    List<GrnDetail> findByGrnNumber(String grnNumber);
    void deleteByGrnNumber(String grnNumber);
}