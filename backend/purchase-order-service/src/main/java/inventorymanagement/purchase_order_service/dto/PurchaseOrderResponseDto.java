package inventorymanagement.purchase_order_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderResponseDto {
    private Integer id;
    private Integer poNumber;
    private String status;
    private LocalDateTime date;
    private List<PurchaseOrderDetailDto> items;

    public PurchaseOrderResponseDto() {}

    public PurchaseOrderResponseDto(Integer id, Integer poNumber, String status, LocalDateTime date, List<PurchaseOrderDetailDto> items) {
        this.id = id;
        this.poNumber = poNumber;
        this.status = status;
        this.date = date;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(Integer poNumber) {
        this.poNumber = poNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<PurchaseOrderDetailDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseOrderDetailDto> items) {
        this.items = items;
    }
}
