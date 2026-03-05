package inventorymanagement.supplier_service.service;

import inventorymanagement.supplier_service.dto.SupplierDTO;
import java.util.List;

public interface SupplierService {
    SupplierDTO createSupplier(SupplierDTO supplierDTO);
    List<SupplierDTO> getAllSuppliers();
    SupplierDTO getSupplierById(Long id);
    SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO);
    void deleteSupplier(Long id);

    // Integrations
    Object getProductsBySupplier(Long supplierId);
    Object getPurchaseOrdersBySupplier(Long supplierId);
    Object approveGRN(Long supplierId, Long grnId);
    Object getAllCompanies();
}
