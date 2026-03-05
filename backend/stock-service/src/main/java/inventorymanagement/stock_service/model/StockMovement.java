package inventorymanagement.stock_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
    @Table(name = "stock_movements")

    public class StockMovement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int movementId;

        @Column(name = "stock_id", nullable = false)
        private int stockId;

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

        public StockMovement(int stockId, String movementType, int quantity, String reason) {
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

        public int getMovementId() {
            return movementId;
        }

        public void setMovementId(int movementId) {
            this.movementId = movementId;
        }

        public int getStockId() {
            return stockId;
        }

        public void setStockId(int stockId) {
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

