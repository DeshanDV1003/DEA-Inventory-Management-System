package inventorymanagement.supplier_service.controller;

import inventorymanagement.supplier_service.dto.SupplierDTO;
import inventorymanagement.supplier_service.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")

    public ResponseEntity<Object> getProductsBySupplier(@PathVariable Long id) {
        Object products = supplierService.getProductsBySupplier(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/purchase-orders")

    public ResponseEntity<Object> getPurchaseOrdersBySupplier(@PathVariable Long id) {
        Object pos = supplierService.getPurchaseOrdersBySupplier(id);
        return ResponseEntity.ok(pos);
    }

    @PutMapping("/{id}/grn/{grnId}/approve")

    public ResponseEntity<Object> approveGRN(@PathVariable Long id, @PathVariable Long grnId) {
        Object result = supplierService.approveGRN(id, grnId);
        return ResponseEntity.ok(result);
    }

    // new endpoint to retrieve companies list from the company service
    @GetMapping("/companies")
    public ResponseEntity<Object> getCompanies() {
        Object companies = supplierService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
}
