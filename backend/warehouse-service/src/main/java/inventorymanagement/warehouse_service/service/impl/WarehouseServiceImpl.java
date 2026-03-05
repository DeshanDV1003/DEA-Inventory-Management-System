/*
 * WarehouseServiceImpl (Service Implementation)
 *
 * Purpose:
 * - Contains business logic related to Warehouse operations.
 * - Handles DTO to Entity conversion.
 * - Communicates with Repository layer.
 *
 * Responsibilities:
 * - Convert RequestDTO → Entity
 * - Save data to database
 * - Convert Entity → ResponseDTO
 * - Perform CRUD operations
 *
 * Architecture:
 * - Acts as middle layer between Controller and Repository.
 * - Called by: PO Service, Stock Transfer Service, GRN Service, Stock Service.
 */

package inventorymanagement.warehouse_service.service.impl;

import inventorymanagement.warehouse_service.dto.WarehouseRequestDto;
import inventorymanagement.warehouse_service.dto.WarehouseResponseDto;
import inventorymanagement.warehouse_service.entity.Warehouse;
import inventorymanagement.warehouse_service.exception.ResourceNotFoundException;
import inventorymanagement.warehouse_service.repository.WarehouseRepository;
import inventorymanagement.warehouse_service.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    // Create new Warehouse record
    @Override
    public WarehouseResponseDto createWarehouse(WarehouseRequestDto request) {

        // Convert DTO to Entity
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setCompanyId(request.getCompanyId());
        warehouse.setEmail(request.getEmail());
        warehouse.setPhone(request.getPhone());
        warehouse.setStatus(request.getStatus());
        warehouse.setAddress(request.getAddress());
        warehouse.setCreatedDate(LocalDateTime.now());

        // Save to database
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        // Convert Entity to ResponseDTO
        return mapToResponseDto(savedWarehouse);
    }

    // Get Warehouse by ID
    @Override
    public WarehouseResponseDto getWarehouseById(Integer id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Warehouse not found with id: " + id));

        return mapToResponseDto(warehouse);
    }

    // Get all Warehouse records
    @Override
    public List<WarehouseResponseDto> getAllWarehouses() {

        return warehouseRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Get Warehouses by Company ID
    @Override
    public List<WarehouseResponseDto> getWarehousesByCompanyId(Integer companyId) {

        return warehouseRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Update Warehouse record
    @Override
    public WarehouseResponseDto updateWarehouse(Integer id, WarehouseRequestDto request) {

        // Check if warehouse exists
        Warehouse existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Warehouse not found with id: " + id));

        // Update fields
        existingWarehouse.setName(request.getName());
        existingWarehouse.setCompanyId(request.getCompanyId());
        existingWarehouse.setEmail(request.getEmail());
        existingWarehouse.setPhone(request.getPhone());
        existingWarehouse.setStatus(request.getStatus());
        existingWarehouse.setAddress(request.getAddress());
        existingWarehouse.setUpdatedDate(LocalDateTime.now());

        // Save updated entity
        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);

        return mapToResponseDto(updatedWarehouse);
    }

    // Delete Warehouse record
    @Override
    public void deleteWarehouse(Integer id) {

        if (!warehouseRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Warehouse not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    // Helper method: Convert Entity → ResponseDTO
    private WarehouseResponseDto mapToResponseDto(Warehouse warehouse) {

        return new WarehouseResponseDto(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getCompanyId(),
                warehouse.getEmail(),
                warehouse.getPhone(),
                warehouse.getStatus(),
                warehouse.getAddress(),
                warehouse.getCreatedDate(),
                warehouse.getUpdatedDate()
        );
    }
}