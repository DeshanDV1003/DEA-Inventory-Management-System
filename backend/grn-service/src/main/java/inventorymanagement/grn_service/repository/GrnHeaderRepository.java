package inventorymanagement.grn_service.repository;

import inventorymanagement.grn_service.entity.GrnHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
Repository interface for managing GRN Header records
Provides CRUD operations and custom query methods for searching and validating GRNs
 */

public interface GrnHeaderRepository extends JpaRepository<GrnHeader, Long> {

    //Find GRN by its unique GRN number
    //@param grnNumber unique GRN number
    //@return Optional containing GRN header if found
    Optional<GrnHeader> findByGrnNumber(String grnNumber);

    /*
    Check if a GRN number already exists
    Used during GRN number generation
    @param grnNumber GRN number to check
    @return true if exists
     */
    boolean existsByGrnNumber(String grnNumber);

    //Filter GRNs by company and warehouse
    List<GrnHeader> findByCompanyIdAndWarehouseId(Long companyId, Long warehouseId);
    //Filter GRNs by company
    List<GrnHeader> findByCompanyId(Long companyId);
    //Filter GRNs by warehouse
    List<GrnHeader> findByWarehouseId(Long warehouseId);
}
