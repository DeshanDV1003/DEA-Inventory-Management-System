package inventorymanagement.purchase_order_service.repository;

import inventorymanagement.purchase_order_service.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository: PurchaseOrderRepository
 * Target Entity: PurchaseOrder (po_headers table)
 *
 * Description:
 * This interface provides the standard CRUD operations for the PurchaseOrder header.
 * Because of the 'cascade = CascadeType.ALL' defined in the Model, saving a
 * PurchaseOrder through this repository will also automatically save/update
 * its associated PurchaseOrderDetails.
 */

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

}
