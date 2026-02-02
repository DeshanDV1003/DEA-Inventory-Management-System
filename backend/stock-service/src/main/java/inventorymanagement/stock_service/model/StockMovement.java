package inventorymanagement.stock_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

