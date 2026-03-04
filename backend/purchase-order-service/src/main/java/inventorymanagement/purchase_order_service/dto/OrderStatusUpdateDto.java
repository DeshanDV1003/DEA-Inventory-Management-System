/**
 * OrderStatusUpdateDto
 *
 * This DTO (Data Transfer Object) is used specifically for partial updates
 * to a Purchase Order, specifically for modifying its lifecycle status.
 *
 * API Usage:
 * - Triggered when client calls:
 *   PATCH /api/v1/purchase-orders/{id}/status
 *
 * Purpose:
 * - Provides a structured way to receive a status change request.
 * - Replaces generic Map structures to allow for formal validation.
 * - Prevents the need to send the entire Purchase Order object when only
 *   the status field needs modification.
 *
 * Included Fields:
 * - status (The new state of the order, e.g., 'APPROVED', 'SHIPPED', 'CANCELLED').
 *
 * Validation:
 * - @NotBlank: Ensures that the status string is not null, not empty, and
 *   contains more than just whitespace.
 *
 * Architecture:
 * - Supports the 'PATCH' method logic by isolating a single field for update.
 * - Simplifies the Service layer by providing a strongly-typed object.
 */

package inventorymanagement.purchase_order_service.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderStatusUpdateDto {
    @NotBlank(message = "Status cannot be empty")
    private String status;

    public OrderStatusUpdateDto() {}
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}