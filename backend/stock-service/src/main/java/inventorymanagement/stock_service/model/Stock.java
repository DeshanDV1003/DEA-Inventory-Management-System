package inventorymanagement.stock_service.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * =========================================================
 * Class Name: Stock
 * =========================================================
 *
 * Purpose:
 * ---------------------------------------------------------
 * This entity represents the stock record of a specific
 * product stored in a specific warehouse under a company.
 * It is used by the Stock Management microservice to track
 * inventory levels and stock thresholds.
 *
 * Each stock record is uniquely identified by the
 * combination of:
 *   - companyId
 *   - productId
 *   - warehouseId
 *
 * This ensures that one product has only one stock entry
 * per warehouse per company.
 *
 * =========================================================
 * Database Mapping:
 * ---------------------------------------------------------
 * Table Name : stocks
 * Unique Key : (company_id, product_id, warehouse_id)
 *
 * =========================================================
 * Data Attributes:
 * ---------------------------------------------------------
 * stockId          : Primary key of the stock record.
 * companyId        : ID of the company that owns the stock.
 * warehouseId      : ID of the warehouse where stock is stored.
 * productId        : ID of the product being tracked.
 * quantity         : Current quantity available in stock.
 * maxStockLevel    : Maximum stock capacity allowed.
 * minStockLevel    : Minimum stock level before alerting.
 * reOrderLevel     : Quantity level at which reordering is required.
 * createdBy        : User who created the stock record.
 * createdDateTime  : Timestamp when the stock was created.
 * updatedBy        : User who last updated the stock record.
 * updatedDateTime  : Timestamp of the last update.
 *
 * =========================================================
 * Lifecycle Callbacks:
 * ---------------------------------------------------------
 * @PrePersist
 *   - Automatically sets createdDateTime when the record
 *     is first inserted into the database.
 *
 * @PreUpdate
 *   - Automatically updates updatedDateTime whenever the
 *     record is modified.
 *
 * =========================================================
 * Design Notes:
 * ---------------------------------------------------------
 * - This class is a JPA entity mapped to the "stocks" table.
 * - It does not maintain foreign key relationships with
 *   Company, Product, or Warehouse entities to preserve
 *   loose coupling in a microservice architecture.
 * - Instead, only their IDs are stored as integer values.
 * - Business logic is handled in the service layer, not here.
 *
 * =========================================================
 */

@Entity
@Table(
        name = "stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"company_id","product_id", "warehouse_id"})
        }
)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockId;

    @Column(name = "company_id", nullable = false)
    private int companyId;

    @Column(name = "warehouse_id", nullable = false)
    private int warehouseId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "max_stock_level")
    private int maxStockLevel;

    @Column(name = "min_stock_level")
    private int minStockLevel;

    @Column(name = "re_order_level")
    private int reOrderLevel;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    // Constructors
    public Stock() {
    }

    public Stock(int stockId, int companyId, int warehouseId, int productId, int quantity, int maxStockLevel, int minStockLevel, int reOrderLevel, String createdBy, LocalDateTime createdDateTime, String updatedBy, LocalDateTime updatedDateTime) {
        this.stockId = stockId;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
        this.maxStockLevel = maxStockLevel;
        this.minStockLevel = minStockLevel;
        this.reOrderLevel = reOrderLevel;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }

    // Auto update timestamps

    @PrePersist
    public void updateCreatedTimestamp() {
        this.createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateUpdatedTimestamp(){
        this.updatedDateTime = LocalDateTime.now();
    }

    // Getters and Setters

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(int maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public int getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(int reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
}
