/*
 * WarehouseRepository
 *
 * Target Entity:
 * - Warehouse (mapped to "warehouses" table)
 *
 * Purpose:
 * - Handles all database operations related to Warehouse records.
 * - Acts as the data access layer between the Service layer and the database.
 *
 * Framework:
 * - Extends JpaRepository<Warehouse, Integer>
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
 * - findByCompanyId(Integer companyId)
 *      Retrieves all warehouse records related to a specific company.
 *
 * - findByStatus(String status)
 *      Retrieves all warehouse records with a given status.
 *
 * Architecture:
 * - Part of the Repository layer in Clean Architecture.
 * - Does not contain business logic.
 * - Communicates directly with the database.
 */

package inventorymanagement.warehouse_service.repository;

import inventorymanagement.warehouse_service.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    // Find all warehouses by company ID
    List<Warehouse> findByCompanyId(Integer companyId);

    // Find all warehouses by status
    List<Warehouse> findByStatus(String status);
}