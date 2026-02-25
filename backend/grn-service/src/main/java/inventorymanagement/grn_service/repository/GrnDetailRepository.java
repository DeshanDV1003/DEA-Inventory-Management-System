package inventorymanagement.grn_service.repository;

import inventorymanagement.grn_service.entity.GrnDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
Repository interface for managing GRN Detail records

Provides CRUD operations and custom query methods related to item level GRN data
 */

public interface GrnDetailRepository extends JpaRepository<GrnDetail, Long> {

    //Retrieve all GRN detail records associated with a specific GRN number
    //@param grnNumber unique GRN number
    //@return list of GrnDetail records

    List<GrnDetail> findByGrnNumber(String grnNumber);

    //Delete all GRN detail records associated with a specific GRN number
    // @param grnNumber unique GRN number
    void deleteByGrnNumber(String grnNumber);
}