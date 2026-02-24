package inventorymanagement.asset_service.repository;

import inventorymanagement.asset_service.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByCompanyId(Long companyId);
    List<Asset> findByDepartmentId(Long departmentId);
    List<Asset> findByWarehouseId(Long warehouseId);
}