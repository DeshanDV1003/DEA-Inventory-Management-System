/*
 * Warehouse Entity
 *
 * This class represents the "warehouses" table in the warehouse_db database.
 *
 * Purpose:
 * - Stores all warehouse records related to companies.
 * - Each record contains warehouse details such as name, email, phone,
 *   address, status, and references to company.
 *
 * Database Mapping:
 * - @Entity tells JPA (Hibernate) that this class is mapped to a database table.
 * - @Table(name = "warehouses") specifies the exact table name.
 * - @Id defines the primary key.
 * - @GeneratedValue(strategy = GenerationType.IDENTITY) enables auto-increment ID.
 *
 * Microservice Architecture:
 * - companyId is a reference to the Company microservice.
 * - Only IDs are stored (no direct entity relationship to other services).
 * - This service is called by: PO Service, Stock Transfer Service, GRN Service, Stock Service.
 *
 * Audit Fields:
 * - createdBy and createdDate store record creation details.
 * - updatedBy and updatedDate store last update information.
 */

package inventorymanagement.warehouse_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // Foreign key reference to Company microservice (ID only)
    private Integer companyId;

    private String email;

    private String phone;

    private String status;

    private String address;

    private String createdBy;

    private LocalDateTime createdDate;

    private String updatedBy;

    private LocalDateTime updatedDate;

    // Default constructor (required by JPA)
    public Warehouse() {
    }

    // Parameterized constructor
    public Warehouse(Integer id, String name, Integer companyId, String email,
                     String phone, String status, String address,
                     String createdBy, LocalDateTime createdDate,
                     String updatedBy, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.address = address;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    // =================== Getters and Setters ===================

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

    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}