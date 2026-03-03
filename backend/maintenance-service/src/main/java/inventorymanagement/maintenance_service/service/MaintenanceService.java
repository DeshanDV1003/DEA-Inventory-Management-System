/*
 * MaintenanceService (Service Interface)
 *
 * Purpose:
 * - Defines business operations related to Maintenance.
 * - Acts as a contract between Controller and Service Implementation.
 *
 * Responsibilities:
 * - Create new maintenance record
 * - Retrieve maintenance by ID
 * - Retrieve all maintenance records
 * - Delete maintenance record
 *
 * Architecture:
 * - Part of the Service layer in clean microservice architecture.
 * - Contains method definitions only (no implementation).
 */

package inventorymanagement.maintenance_service.service;

import inventorymanagement.maintenance_service.dto.AddMaintenanceRequestDto;
import inventorymanagement.maintenance_service.dto.MaintenanceResponseDto;
import java.util.List;

public interface MaintenanceService {

    // Create new Maintenance record
    MaintenanceResponseDto createMaintenance(AddMaintenanceRequestDto request);

    // Get Maintenance by ID
    MaintenanceResponseDto getMaintenanceById(Integer id);

    // Get all Maintenance records
    List<MaintenanceResponseDto> getAllMaintenances();


    // Update existing Maintenance record
    MaintenanceResponseDto updateMaintenance(Integer id, AddMaintenanceRequestDto request);

    // Delete Maintenance by ID
    void deleteMaintenance(Integer id);
}