/*
 * MaintenanceStatusRepository
 *
 * Target Entity:
 * - MaintenanceStatus (mapped to "maintenance_status" table)
 *
 * Purpose:
 * - Handles all database operations related to MaintenanceStatus records.
 * - Acts as the data access layer for maintenance status information.
 *
 * Framework:
 * - Extends JpaRepository<MaintenanceStatus, Integer>
 * - Spring Data JPA automatically generates the implementation at runtime.
 *
 * Built-in CRUD Operations Provided:
 * - save()
 * - findById()
 * - findAll()
 * - deleteById()
 * - existsById()
 * - count()
 *
 * Usage in Service Layer:
 * - Used to retrieve status records (e.g., Pending, In Progress, Completed).
 * - Used when converting statusId from DTO into a MaintenanceStatus entity.
 *
 * Architecture:
 * - Part of the Repository layer in clean microservice architecture.
 * - Does not contain business logic.
 * - Communicates directly with the database through JPA.
 */

package inventorymanagement.maintenance_service.repository;

import inventorymanagement.maintenance_service.entity.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * For the  MaintenanceStatus (maintenance_status table)
 * This interface handles database operations for MaintenanceStatus records.
 * Provides CRUD functionality through JpaRepository.
 */
public interface MaintenanceStatusRepository extends JpaRepository<MaintenanceStatus, Integer> {

}