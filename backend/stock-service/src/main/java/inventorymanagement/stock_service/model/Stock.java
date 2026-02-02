package inventorymanagement.stock_service.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * =========================================================
 * Class Name: Stock
 * =========================================================
 * Purpose:
 * This entity represents the stock information of an asset
 * in a specific warehouse. It keeps track of how many units
 * of a particular asset are available in a given warehouse.
 *
 * This class belongs to the Stock Management microservice
 * and does NOT store full Asset or Warehouse objects.
 * Instead, it stores only their IDs to maintain loose
 * coupling between microservices.
 *
 * =========================================================
 * Data Attributes:
 * ---------------------------------------------------------
 * stockId         : Unique identifier for each stock record.
 * assetId         : ID of the asset (from Asset Service).
 * warehouseId     : ID of the warehouse (from Warehouse Service).
 * quantity        : Total quantity of the asset in the warehouse.
 * reservedQuantity: Quantity reserved for assignments or orders.
 * minThreshold    : Minimum stock level before triggering alerts.
 * lastUpdated     : Timestamp of the last stock update.
 *
 * =========================================================
 * Methods:
 * ---------------------------------------------------------
 * updateTimestamp(): Automatically updates the lastUpdated
 *                    field whenever the entity is inserted
 *                    or updated in the database.
 *
 * Getters & Setters:
 * Standard accessor and mutator methods for all attributes.
 *
 * =========================================================
 * Design Notes:
 * ---------------------------------------------------------
 * - assetId and warehouseId are stored as simple Long values
 *   instead of foreign key relationships.
 * - This design supports microservice architecture by
 *   avoiding tight coupling with Asset and Warehouse services.
 * - A unique constraint is applied on (assetId, warehouseId)
 *   to prevent duplicate stock records.
 * =========================================================
 */

@Entity
@Table(
        name = "stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"asset_id", "warehouse_id"})
        }
)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "reserved_quantity")
    private int reservedQuantity;

    @Column(name = "min_threshold")
    private int minThreshold;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructors
    public Stock() {
    }

    public Stock(Long assetId, Long warehouseId, int quantity, int reservedQuantity, int minThreshold) {
        this.assetId = assetId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
        this.minThreshold = minThreshold;
    }

    // Auto update timestamp
    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
