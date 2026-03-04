/*
 * MaintenanceRepository
 *
 * Target Entity:
 * - Maintenance (mapped to "maintenances" table)
 *
 * Purpose:
 * - Handles all database operations related to Maintenance records.
 * - Acts as the data access layer between the Service layer and the database.
 *
 * Framework:
 * - Extends JpaRepository<Maintenance, Integer>
 * - Spring Data JPA automatically provides implementation at runtime.
 *
 * Built-in CRUD Operations Provided by JpaRepository:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 * - count()
 *
 * Custom Query Methods:
 * - findByAssetId(Integer assetId)
 *      Retrieves all maintenance records related to a specific asset.
 *
 * - findByCompanyId(Integer companyId)
 *      Retrieves all maintenance records related to a specific company.
 *
 * Architecture:
 * - Part of the Repository layer in Clean Architecture.
 * - Does not contain business logic.
 * - Communicates directly with the database.
 */

package inventorymanagement.maintenance_service.repository;

import inventorymanagement.maintenance_service.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * For the Maintenance (maintenances table)
 * This interface handles database operations for Maintenance records.
 * It extends JpaRepository to provide built-in CRUD operations.
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    // Find all maintenance records by using assetId
    List<Maintenance> findByAssetId(Integer assetId);

    // Find all maintenance records by using companyId
    List<Maintenance> findByCompanyId(Integer companyId);

}
