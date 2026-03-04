package inventorymanagement.warehouse_service.controller;

import inventorymanagement.warehouse_service.dto.WarehouseDTO;
import inventorymanagement.warehouse_service.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO dto) {
        WarehouseDTO created = warehouseService.createWarehouse(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> update(@PathVariable Long id, @RequestBody WarehouseDTO dto) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/purchase-orders")
    public ResponseEntity<Object> getPurchaseOrders(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getPurchaseOrdersByWarehouse(id));
    }

    @GetMapping("/{id}/stock-transfers")
    public ResponseEntity<Object> getStockTransfers(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getStockTransfersByWarehouse(id));
    }

    @GetMapping("/{id}/grns")
    public ResponseEntity<Object> getGrns(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getGRNsByWarehouse(id));
    }

    @GetMapping("/{id}/stocks")
    public ResponseEntity<Object> getStocks(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getStockByWarehouse(id));
    }

    @GetMapping("/companies")
    public ResponseEntity<Object> getCompanies() {
        return ResponseEntity.ok(warehouseService.getAllCompanies());
    }
}