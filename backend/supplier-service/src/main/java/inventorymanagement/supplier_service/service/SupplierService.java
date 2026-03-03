package inventorymanagement.supplier_service.service;

import inventorymanagement.supplier_service.dto.SupplierDTO;
import java.util.List;

public interface SupplierService {
    SupplierDTO createSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> getAllSuppliers();

    SupplierDTO getSupplierById(Long id);

    SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO);

    void deleteSupplier(Long id);

    Object getProductsBySupplier(Long supplierId);

    Object getPurchaseOrdersBySupplier(Long supplierId);

    Object approveGRN(Long supplierId, Long grnId);

    /**
     * Fetches a list of companies via the company service.
     * The returned object is typically a List of simple company representations
     * (e.g. id/name pairs) and is forwarded directly to the caller.
     */
    Object getAllCompanies();
}
