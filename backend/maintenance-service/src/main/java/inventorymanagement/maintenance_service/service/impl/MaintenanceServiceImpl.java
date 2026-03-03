/*
 * MaintenanceServiceImpl (Service Implementation)
 *
 * Purpose:
 * - Contains business logic related to Maintenance operations.
 * - Handles DTO to Entity conversion.
 * - Communicates with Repository layer.
 *
 * Responsibilities:
 * - Validate status existence
 * - Convert RequestDTO → Entity
 * - Save data to database
 * - Convert Entity → ResponseDTO
 * - Perform CRUD operations
 *
 * Architecture:
 * - Acts as middle layer between Controller and Repository.
 */

package inventorymanagement.maintenance_service.service.impl;


import inventorymanagement.maintenance_service.dto.AddMaintenanceRequestDto;
import inventorymanagement.maintenance_service.dto.MaintenanceResponseDto;
import inventorymanagement.maintenance_service.entity.Maintenance;
import inventorymanagement.maintenance_service.entity.MaintenanceStatus;
import inventorymanagement.maintenance_service.exception.ResourceNotFoundException;
import inventorymanagement.maintenance_service.repository.MaintenanceRepository;
import inventorymanagement.maintenance_service.repository.MaintenanceStatusRepository;
import inventorymanagement.maintenance_service.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceStatusRepository maintenanceStatusRepository;

    // Create new Maintenance record
    @Override
    public MaintenanceResponseDto createMaintenance(AddMaintenanceRequestDto request) {

        // Find status by ID
        MaintenanceStatus status = maintenanceStatusRepository
                .findById(request.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance Status not found with id: " + request.getStatusId()));

        // Convert DTO to Entity
        Maintenance maintenance = new Maintenance();
        maintenance.setMaintenanceNumber(request.getMaintenanceNumber());
        maintenance.setCompanyId(request.getCompanyId());
        maintenance.setWarehouseId(request.getWarehouseId());
        maintenance.setAssetId(request.getAssetId());
        maintenance.setDate(request.getDate());
        maintenance.setCost(request.getCost());
        maintenance.setDescription(request.getDescription());
        maintenance.setStatus(status);
        maintenance.setCreatedDate(LocalDateTime.now());

        // Save to database
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        // Convert Entity to ResponseDTO
        return mapToResponseDto(savedMaintenance);
    }

    // Get Maintenance by ID
    @Override
    public MaintenanceResponseDto getMaintenanceById(Integer id) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance not found with id: " + id ));

        return mapToResponseDto(maintenance);
    }

    // Get all Maintenance records
    @Override
    public List<MaintenanceResponseDto> getAllMaintenances() {

        return maintenanceRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Update Maintenance record
    @Override
    public MaintenanceResponseDto updateMaintenance(Integer id, AddMaintenanceRequestDto request) {

        // Check if maintenance exists
        Maintenance existingMaintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance not found with id: " + id));

        // Check if status exists
        MaintenanceStatus status = maintenanceStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance Status not found with id: " + request.getStatusId()));

        // Update fields
        existingMaintenance.setMaintenanceNumber(request.getMaintenanceNumber());
        existingMaintenance.setCompanyId(request.getCompanyId());
        existingMaintenance.setWarehouseId(request.getWarehouseId());
        existingMaintenance.setAssetId(request.getAssetId());
        existingMaintenance.setDate(request.getDate());
        existingMaintenance.setCost(request.getCost());
        existingMaintenance.setDescription(request.getDescription());
        existingMaintenance.setStatus(status);
        existingMaintenance.setModifiedDate(LocalDateTime.now());

        // Save updated entity
        Maintenance updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return mapToResponseDto(updatedMaintenance);
    }


    // Delete Maintenance record
    @Override
    public void deleteMaintenance(Integer id) {

        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Maintenance not found with id: " + id
            );
        }
        maintenanceRepository.deleteById(id);
    }

    // Helper method: Convert Entity → ResponseDTO
    private MaintenanceResponseDto mapToResponseDto(Maintenance maintenance) {

        return new MaintenanceResponseDto(
                maintenance.getId(),
                maintenance.getMaintenanceNumber(),
                maintenance.getCompanyId(),
                maintenance.getWarehouseId(),
                maintenance.getAssetId(),
                maintenance.getDate(),
                maintenance.getCost(),
                maintenance.getDescription(),
                maintenance.getStatus().getId(),
                maintenance.getCreatedDate(),
                maintenance.getModifiedDate()
        );
    }
}