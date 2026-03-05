/*
 * WarehouseResponseDto
 *
 * This DTO (Data Transfer Object) is used to send Warehouse data
 * from the backend to the client.
 *
 * API Usage:
 * - Used when client calls:
 *   GET /api/v1/warehouses
 *   GET /api/v1/warehouses/{id}
 *
 * Purpose:
 * - Transfers warehouse information from the Service layer
 *   to the Controller and then to the client.
 * - Ensures that only necessary and safe data is exposed.
 *
 * Included Fields:
 * - id
 * - name
 * - companyId
 * - email
 * - phone
 * - status
 * - address
 * - createdDate
 * - updatedDate
 */

package inventorymanagement.warehouse_service.dto;

import java.time.LocalDateTime;

public class WarehouseResponseDto {

    private Integer id;
    private String name;
    private Integer companyId;
    private String email;
    private String phone;
    private String status;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public WarehouseResponseDto() {
    }

    public WarehouseResponseDto(Integer id, String name, Integer companyId, String email,
                                String phone, String status, String address,
                                LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.address = address;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getCompanyId() {
        return companyId;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getStatus() {
        return status;
    }
    public String getAddress() {
        return address;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}