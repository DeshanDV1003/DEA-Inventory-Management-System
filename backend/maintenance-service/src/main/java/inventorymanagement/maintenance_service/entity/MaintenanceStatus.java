/*
 * MaintenanceStatus Entity
 *
 * This class represents the "maintenance_status" table in the maintenance_db database.
 *
 * Purpose:
 * - Stores different status types of maintenance records.
 *   (Example: Pending, In Progress, Completed, Cancelled)
 *
 * Relationship:
 * - One MaintenanceStatus can be linked to many Maintenance records (One-to-Many).
 * - This is part of a bidirectional relationship with the Maintenance entity.
 *
 * JPA Usage:
 * - @Entity tells Hibernate that this class is a database table.
 * - @Table maps this class to the "maintenance_status" table.
 * - @Id defines the primary key.
 * - @GeneratedValue enables auto-increment for the ID.
 * - @OneToMany defines the relationship with Maintenance.
 *
 * In Microservice Architecture:
 * - This entity belongs only to the Maintenance Service database.
 * - It does not connect directly to other microservices.
 */

package inventorymanagement.maintenance_service.entity;

import jakarta.persistence.*;
import java.util.List;


 // This class represents a database table.
@Entity

@Table(name = "maintenance_status")
public class MaintenanceStatus {


    // This field is primary key
    @Id

    // Primary key is generated automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    //One MaintenanceStatus can have many Maintenance records, 1:M.
     // Using fetch - data will load only when needed
    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<Maintenance> maintenances;


    //  =================== Constructors   ===================

    // Default constructor
     // For JPA - (Java Persistence API) Connect java object(classes) with db tables
    public MaintenanceStatus() {
    }

    // Parameterized constructor
    public MaintenanceStatus(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

     //  =================== Getters and Setters   ===================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

     //  =================== toString Method   ===================
    @Override
    public String toString() {
        return "MaintenanceStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}