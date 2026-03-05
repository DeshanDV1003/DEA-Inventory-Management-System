/*
 * WarehouseService (Service Interface)
 *
 * Purpose:
 * - Defines business operations related to Warehouse.
 * - Acts as a contract between Controller and Service Implementation.
 *
 * Responsibilities:
 * - Create new warehouse record
 * - Retrieve warehouse by ID
 * - Retrieve all warehouse records
 * - Retrieve warehouses by company ID
 * - Update warehouse record
 * - Delete warehouse record
 *
 * Architecture:
 * - Part of the Service layer in clean microservice architecture.
 * - Contains method definitions only (no implementation).
 */

package inventorymanagement.warehouse_service.service;

import inventorymanagement.warehouse_service.dto.WarehouseRequestDto;
import inventorymanagement.warehouse_service.dto.WarehouseResponseDto;

import java.util.List;

public interface WarehouseService {

    // Create new Warehouse record
    WarehouseResponseDto createWarehouse(WarehouseRequestDto request);

    // Get Warehouse by ID
    WarehouseResponseDto getWarehouseById(Integer id);

    // Get all Warehouse records
    List<WarehouseResponseDto> getAllWarehouses();

    // Get Warehouses by Company ID
    List<WarehouseResponseDto> getWarehousesByCompanyId(Integer companyId);

    // Update existing Warehouse record
    WarehouseResponseDto updateWarehouse(Integer id, WarehouseRequestDto request);

    // Delete Warehouse by ID
    void deleteWarehouse(Integer id);
}