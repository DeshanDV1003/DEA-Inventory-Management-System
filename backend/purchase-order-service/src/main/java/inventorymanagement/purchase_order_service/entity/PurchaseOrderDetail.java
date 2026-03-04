package inventorymanagement.purchase_order_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * PurchaseOrderDetail Entity
 *
 * This entity represents the 'po_details' table. It stores the specific items
 * associated with a Purchase Order header.
 *
 * Purpose:
 * - Captures the granular details (products and quantities) of a purchase.
 *
 * Variables & Data Types:
 * - Integer id: Primary key (Auto-incremented).
 * - PurchaseOrder purchaseOrder: The parent header this item belongs to.
 * - Integer productId: Links to the external Product Service.
 * - Integer quantity: The amount of product being ordered.
 * - Audit Fields: Tracking who created/updated this specific line item.
 *
 * Annotations used:
 * - @ManyToOne: Defines the relationship back to the PurchaseOrder header.
 * - @JoinColumn(name = "po_header_id"): Specifies the foreign key column name.
 * - @Column(nullable = false): Ensures data integrity for required fields.
 */

@Entity
@Table(name = "po_details")
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_header_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_by", length = 100)
    private String createdBy;

        @Column(name = "created_date")
        private LocalDateTime createdDate;

        @Column(name = "updated_by", length = 100)
        private String updatedBy;

        @Column(name = "updated_date")
        private LocalDateTime updatedDate;


        public PurchaseOrderDetail() {
        }


        public PurchaseOrderDetail(Integer id, PurchaseOrder purchaseOrder, Integer productId, Integer quantity, String createdBy, LocalDateTime createdDate, String updatedBy, LocalDateTime updatedDate) {
            this.id = id;
            this.purchaseOrder = purchaseOrder;
            this.productId = productId;
            this.quantity = quantity;
            this.createdBy = createdBy;
            this.createdDate = createdDate;
            this.updatedBy = updatedBy;
            this.updatedDate = updatedDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public PurchaseOrder getPurchaseOrder() {
            return purchaseOrder;
        }

        public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
            this.purchaseOrder = purchaseOrder;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        @Override
        public String toString() {
            return "PurchaseOrderDetail{" +
                    "id=" + id +
                    ", purchaseOrder=" + purchaseOrder +
                    ", productId=" + productId +
                    ", quantity=" + quantity +
                    ", createdBy='" + createdBy + '\'' +
                    ", createdDate=" + createdDate +
                    ", updatedBy='" + updatedBy + '\'' +
                    ", updatedDate=" + updatedDate +
                    '}';
        }
    }

