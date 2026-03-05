/*
 * MaintenanceResponseDto
 *
 * This DTO (Data Transfer Object) is used to send Maintenance data
 * from the backend to the client.
 *
 * API Usage:
 * - Used when client calls:
 *   GET /api/v1/maintenances
 *   GET /api/v1/maintenances/{id}
 *
 * Purpose:
 * - Transfers maintenance information from the Service layer
 *   to the Controller and then to the client.
 * - Ensures that only necessary and safe data is exposed.
 *
 * Included Fields:
 * - id (Primary Key)
 * - maintenanceNumber
 * - companyId
 * - warehouseId
 * - assetId
 * - date
 * - cost
 * - description
 * - statusId
 * - createdDate
 * - modifiedDate
 *
 * Architecture:
 * - Separates internal entity structure from API response.
 * - Supports clean architecture and microservice principles.
 * - Prevents direct exposure of database entity objects.
 */

package inventorymanagement.maintenance_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * When called GET /api/v1/maintenances
 * Used to send maintenance data back to client.
 * and only include what client need to see
 */
public class MaintenanceResponseDto {

    private Integer id;
    private String maintenanceNumber;
    private Integer companyId;
    private Integer warehouseId;
    private Integer assetId;
    private LocalDateTime date;
    private BigDecimal cost;
    private String description;
    private Integer statusId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MaintenanceResponseDto() {
    }

    public MaintenanceResponseDto(Integer id,
                                  String maintenanceNumber,
                                  Integer companyId,
                                  Integer warehouseId,
                                  Integer assetId,
                                  LocalDateTime date,
                                  BigDecimal cost,
                                  String description,
                                  Integer statusId,
                                  LocalDateTime createdDate,
                                  LocalDateTime modifiedDate) {
        this.id = id;
        this.maintenanceNumber = maintenanceNumber;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.assetId = assetId;
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.statusId = statusId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public String getMaintenanceNumber() {
        return maintenanceNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}