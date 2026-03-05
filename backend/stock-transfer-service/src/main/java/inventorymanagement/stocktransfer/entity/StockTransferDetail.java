package inventorymanagement.stocktransfer.entity;

import jakarta.persistence.*;

/**
 * Detail lines for Stock Transfer.
 * Many details belong to one header.
 */
@Entity
@Table(
        name = "stock_transfer_detail",
        indexes = {
                @Index(name = "idx_detail_product", columnList = "product_id")
        }
)
public class StockTransferDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many detail records belong to one header.
     * Fetch LAZY to avoid loading details when listing headers.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "header_id", nullable = false)
    private StockTransferHeader header;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "requested_qty", nullable = false)
    private Integer requestedQty;

    @Column(name = "transfer_qty", nullable = false)
    private Integer transferQty;

    @Column(name = "received_qty", nullable = false)
    private Integer receivedQty;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "remark", length = 255)
    private String remark;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StockTransferHeader getHeader() { return header; }
    public void setHeader(StockTransferHeader header) { this.header = header; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getRequestedQty() { return requestedQty; }
    public void setRequestedQty(Integer requestedQty) { this.requestedQty = requestedQty; }

    public Integer getTransferQty() { return transferQty; }
    public void setTransferQty(Integer transferQty) { this.transferQty = transferQty; }

    public Integer getReceivedQty() { return receivedQty; }
    public void setReceivedQty(Integer receivedQty) { this.receivedQty = receivedQty; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}