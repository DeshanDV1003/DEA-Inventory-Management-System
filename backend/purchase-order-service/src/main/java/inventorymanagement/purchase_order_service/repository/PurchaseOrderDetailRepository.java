package inventorymanagement.purchase_order_service.repository;

import inventorymanagement.purchase_order_service.entity.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository: PurchaseOrderDetailRepository
 * Target Entity: PurchaseOrderDetail (po_details table)
 *
 * Description:
 * This interface handles data access for individual line items within purchase orders.
 * While details are often managed via the PurchaseOrder header, this repository
 * is useful for specific queries like finding all orders containing a specific product.
 */
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    List<PurchaseOrderDetail> findByProductId(Integer productId);
}
