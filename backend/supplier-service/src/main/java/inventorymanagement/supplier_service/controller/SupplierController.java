package inventorymanagement.supplier_service.controller;

import inventorymanagement.supplier_service.dto.SupplierDTO;
import inventorymanagement.supplier_service.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO dto) {
        return new ResponseEntity<>(supplierService.createSupplier(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO dto) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Object> getProductsBySupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getProductsBySupplier(id));
    }

    @GetMapping("/{id}/purchase-orders")
    public ResponseEntity<Object> getPurchaseOrdersBySupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getPurchaseOrdersBySupplier(id));
    }

    @PutMapping("/{id}/grn/{grnId}/approve")
    public ResponseEntity<Object> approveGRN(@PathVariable Long id, @PathVariable Long grnId) {
        return ResponseEntity.ok(supplierService.approveGRN(id, grnId));
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> getCompanies() {
        return ResponseEntity.ok(supplierService.getAllCompanies());
    }
}
