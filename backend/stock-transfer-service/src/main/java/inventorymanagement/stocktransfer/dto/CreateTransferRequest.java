package inventorymanagement.stocktransfer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Request DTO to create a new Stock Transfer.
 * This DTO is used by Controller. It should not be an Entity.
 */
public class CreateTransferRequest {

    @NotNull(message = "fromWarehouseId is required")
    private Long fromWarehouseId;

    @NotNull(message = "toWarehouseId is required")
    private Long toWarehouseId;

    @Size(max = 255, message = "remark must be max 255 characters")
    private String remark;

    @Size(max = 100, message = "createdBy must be max 100 characters")
    private String createdBy;

    @NotEmpty(message = "items cannot be empty")
    @Valid // ✅ validates each item inside the list
    private List<TransferItemRequest> items;

    // getters & setters
    public Long getFromWarehouseId() { return fromWarehouseId; }
    public void setFromWarehouseId(Long fromWarehouseId) { this.fromWarehouseId = fromWarehouseId; }

    public Long getToWarehouseId() { return toWarehouseId; }
    public void setToWarehouseId(Long toWarehouseId) { this.toWarehouseId = toWarehouseId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public List<TransferItemRequest> getItems() { return items; }
    public void setItems(List<TransferItemRequest> items) { this.items = items; }
}