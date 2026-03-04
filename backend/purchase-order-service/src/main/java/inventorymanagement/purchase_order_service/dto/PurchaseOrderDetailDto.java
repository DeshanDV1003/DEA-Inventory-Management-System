/**
 * PurchaseOrderDetailDto
 *
 * This DTO represents an individual line item (product and quantity)
 * within a Purchase Order.
 *
 * API Usage:
 * - Nested within PurchaseOrderRequestDto for:
 *   POST /api/v1/purchase-orders
 *   PUT /api/v1/purchase-orders/{id}
 * - Nested within PurchaseOrderResponseDto for:
 *   GET /api/v1/purchase-orders
 *
 * Purpose:
 * - Transfers granular item data between the client and the server.
 * - Includes audit fields to provide transparency on when specific line
 *   items were added or modified.
 *
 * Included Fields:
 * - productId (Reference to the external Product Service).
 * - quantity (The amount of the product being requested).
 * - createdBy / updatedBy (Audit tracking for user actions).
 * - createdDate / updatedDate (Timestamps for record creation and modification).
 *
 * Validation:
 * - @NotNull: Ensures both productId and quantity are provided in the request.
 * - @Min(1): Enforces business logic that an item must have at least a quantity of 1.
 *
 * Architecture:
 * - Acts as a bridge between the 'po_details' database entity and the JSON API layer.
 * - Decouples the database internal structure from the data presented to the Frontend.
 */

package inventorymanagement.purchase_order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PurchaseOrderDetailDto {
    @NotNull(message = "Product ID is required for each item")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

    public PurchaseOrderDetailDto() {}

    // Getters and Setters
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}