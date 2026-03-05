package inventorymanagement.stocktransfer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Each item line in a stock transfer request.
 */
public class TransferItemRequest {

    @NotNull(message = "productId is required")
    private Long productId;

    @NotNull(message = "qty is required")
    @Positive(message = "qty must be greater than 0")
    private Integer qty;

    // getters & setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
}