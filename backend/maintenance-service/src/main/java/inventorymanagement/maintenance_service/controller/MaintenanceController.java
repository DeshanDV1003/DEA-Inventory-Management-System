/*
 * MaintenanceController
 *
 * Purpose:
 * - Exposes REST API endpoints for Maintenance operations.
 * - Handles HTTP requests from client (Postman / Frontend).
 * - Delegates business logic to MaintenanceService.
 *
 * Responsibilities:
 * - Accept client requests (POST, GET, DELETE)
 * - Validate and receive request body data
 * - Call Service layer methods
 * - Return appropriate HTTP responses
 *
 * Architecture:
 * - Part of Controller layer in microservice architecture.
 * - Does NOT contain business logic.
 * - Only communicates with Service layer.
 *
 * Base URL:
 * - /api/v1/maintenances
 */

package inventorymanagement.maintenance_service.controller;

import inventorymanagement.maintenance_service.dto.AddMaintenanceRequestDto;
import inventorymanagement.maintenance_service.dto.MaintenanceResponseDto;
import inventorymanagement.maintenance_service.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    // Constructor Injection (Recommended)
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }


    // Create new Maintenance record
    // Endpoint: POST /api/v1/maintenances

    @PostMapping
    public ResponseEntity<MaintenanceResponseDto> createMaintenance(
            @Valid @RequestBody AddMaintenanceRequestDto request) {

        MaintenanceResponseDto response =
                maintenanceService.createMaintenance(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Maintenance by ID
    // Endpoint: PUT /api/v1/maintenances/{id}

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> getMaintenanceById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                maintenanceService.getMaintenanceById(id)
        );
    }

    // Get all Maintenance records
    // Endpoint: GET /api/v1/maintenances

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDto>> getAllMaintenances() {

        return ResponseEntity.ok(
                maintenanceService.getAllMaintenances()
        );
    }


    // Update Maintenance by ID
    // Endpoint: Update /api/v1/maintenances/{id}

    @PutMapping("/{id}")
    public MaintenanceResponseDto updateMaintenance(
            @PathVariable Integer id,
            @Valid @RequestBody AddMaintenanceRequestDto request) {

        return maintenanceService.updateMaintenance(id, request);
    }

    // Delete Maintenance by ID
    // Endpoint: DELETE /api/v1/maintenances/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenance(
            @PathVariable Integer id) {

        maintenanceService.deleteMaintenance(id);

        return ResponseEntity.ok("Maintenance deleted successfully.");
    }
}