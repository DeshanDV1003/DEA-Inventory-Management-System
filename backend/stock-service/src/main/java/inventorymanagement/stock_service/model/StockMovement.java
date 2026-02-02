package inventorymanagement.stock_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * =========================================================
 * Class Name: StockMovement
 * =========================================================
 * Purpose:
 * This entity records every change in stock quantity.
 * It acts as a transaction log for stock operations such as:
 *  - Stock increase (IN)
 *  - Stock decrease (OUT)
 *  - Stock transfer (TRANSFER)
 *
 * This helps in:
 *  - Auditing stock changes
 *  - Tracking stock history
 *  - Debugging incorrect stock updates
 *
 * =========================================================
 * Data Attributes:
 * ---------------------------------------------------------
 * movementId   : Unique identifier for each stock movement.
 * stockId      : ID of the related stock record.
 * movementType : Type of movement (IN, OUT, TRANSFER).
 * quantity     : Number of units moved.
 * reason       : Reason for movement (purchase, assignment,
 *                maintenance, damage, etc.).
 * movementTime : Timestamp of when the movement occurred.
 *
 * =========================================================
 * Methods:
 * ---------------------------------------------------------
 * setMovementTime(): Automatically sets the movementTime
 *                    when a new movement record is created.
 *
 * Getters & Setters:
 * Standard accessor and mutator methods for all attributes.
 *
 * =========================================================
 * Design Notes:
 * ---------------------------------------------------------
 * - stockId is stored as a simple Long value instead of a
 *   foreign key relationship to the Stock entity.
 * - This design avoids tight coupling and supports
 *   independent service scalability.
 * =========================================================
 */
@Entity
    @Table(name = "stock_movements")

    public class StockMovement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long movementId;

        @Column(name = "stock_id", nullable = false)
        private Long stockId;

        @Column(name = "movement_type", nullable = false)
        private String movementType; // IN, OUT, TRANSFER

        @Column(nullable = false)
        private int quantity;

        @Column
        private String reason;

        @Column(name = "movement_time")
        private LocalDateTime movementTime;

        // Constructors
        public StockMovement() {
        }

        public StockMovement(Long stockId, String movementType, int quantity, String reason) {
            this.stockId = stockId;
            this.movementType = movementType;
            this.quantity = quantity;
            this.reason = reason;
        }

        // Auto timestamp
        @PrePersist
        public void setMovementTime() {
            this.movementTime = LocalDateTime.now();
        }

        // Getters and Setters

        public Long getMovementId() {
            return movementId;
        }

        public void setMovementId(Long movementId) {
            this.movementId = movementId;
        }

        public Long getStockId() {
            return stockId;
        }

        public void setStockId(Long stockId) {
            this.stockId = stockId;
        }

        public String getMovementType() {
            return movementType;
        }

        public void setMovementType(String movementType) {
            this.movementType = movementType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public LocalDateTime getMovementTime() {
            return movementTime;
        }

        public void setMovementTime(LocalDateTime movementTime) {
            this.movementTime = movementTime;
        }
    }

