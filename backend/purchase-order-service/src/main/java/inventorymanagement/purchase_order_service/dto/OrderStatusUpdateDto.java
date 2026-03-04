package inventorymanagement.purchase_order_service.dto;

import jakarta.validation.constraints.NotBlank;

public class OrderStatusUpdateDto {
    @NotBlank(message = "Status cannot be empty")
    private String status;

    public OrderStatusUpdateDto() {}
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}