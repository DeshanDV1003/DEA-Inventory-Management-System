package inventorymanagement.grn_service.repository;

import inventorymanagement.grn_service.entity.GrnHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrnHeaderRepository extends JpaRepository<GrnHeader, Long> {
    Optional<GrnHeader> findByGrnNumber(String grnNumber);
    boolean existsByGrnNumber(String grnNumber);

    List<GrnHeader> findByCompanyIdAndDepartmentIdAndWarehouseId(Long companyId, Long departmentId, Long warehouseId);
    List<GrnHeader> findByCompanyId(Long companyId);
    List<GrnHeader> findByDepartmentId(Long departmentId);
    List<GrnHeader> findByWarehouseId(Long warehouseId);
}
