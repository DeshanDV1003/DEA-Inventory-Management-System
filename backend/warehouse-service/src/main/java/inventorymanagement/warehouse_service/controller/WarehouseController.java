/*
 * WarehouseController
 *
 * Purpose:
 * - Exposes REST API endpoints for Warehouse operations.
 * - Handles HTTP requests from client (Postman / Frontend).
 * - Delegates business logic to WarehouseService.
 *
 * Responsibilities:
 * - Accept client requests (POST, GET, PUT, DELETE)
 * - Validate and receive request body data
 * - Call Service layer methods
 * - Return appropriate HTTP responses
 *
 * Architecture:
 * - Part of Controller layer in microservice architecture.
 * - Does NOT contain business logic.
 * - Only communicates with Service layer.
 * - Called by: PO Service, Stock Transfer Service, GRN Service, Stock Service.
 *
 * Base URL:
 * - /api/v1/warehouses
 */

package inventorymanagement.warehouse_service.controller;

import inventorymanagement.warehouse_service.dto.WarehouseRequestDto;
import inventorymanagement.warehouse_service.dto.WarehouseResponseDto;
import inventorymanagement.warehouse_service.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    // Constructor Injection (Recommended)
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // Create new Warehouse record
    // Endpoint: POST /api/v1/warehouses
    @PostMapping
    public ResponseEntity<WarehouseResponseDto> createWarehouse(
            @Valid @RequestBody WarehouseRequestDto request) {

        WarehouseResponseDto response = warehouseService.createWarehouse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Warehouse by ID
    // Endpoint: GET /api/v1/warehouses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto> getWarehouseById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    // Get all Warehouse records
    // Endpoint: GET /api/v1/warehouses
    @GetMapping
    public ResponseEntity<List<WarehouseResponseDto>> getAllWarehouses() {

        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    // Get Warehouses by Company ID
    // Endpoint: GET /api/v1/warehouses/company/{companyId}
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<WarehouseResponseDto>> getWarehousesByCompanyId(
            @PathVariable Integer companyId) {

        return ResponseEntity.ok(warehouseService.getWarehousesByCompanyId(companyId));
    }

    // Update Warehouse by ID
    // Endpoint: PUT /api/v1/warehouses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto> updateWarehouse(
            @PathVariable Integer id,
            @Valid @RequestBody WarehouseRequestDto request) {

        return ResponseEntity.ok(warehouseService.updateWarehouse(id, request));
    }

    // Delete Warehouse by ID
    // Endpoint: DELETE /api/v1/warehouses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(
            @PathVariable Integer id) {

        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Warehouse deleted successfully.");
    }
}