package inventorymanagement.stocktransfer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Header table for Stock Transfer.
 * One header has multiple detail lines.
 */
@Entity
@Table(
        name = "stock_transfer_header",
        indexes = {
                @Index(name = "idx_transfer_no", columnList = "transfer_no", unique = true),
                @Index(name = "idx_transfer_status", columnList = "status")
        }
)
public class StockTransferHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique business identifier (ex: TR-ABCDE)
    @Column(name = "transfer_no", nullable = false, unique = true, length = 20)
    private String transferNo;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "from_warehouse_id", nullable = false)
    private Long fromWarehouseId;

    @Column(name = "to_warehouse_id", nullable = false)
    private Long toWarehouseId;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * Bidirectional mapping for convenience.
     * If you don't need it, you can remove this list.
     */
    @OneToMany(
            mappedBy = "header",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<StockTransferDetail> details = new ArrayList<>();

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransferNo() { return transferNo; }
    public void setTransferNo(String transferNo) { this.transferNo = transferNo; }

    public LocalDateTime getTransferDate() { return transferDate; }
    public void setTransferDate(LocalDateTime transferDate) { this.transferDate = transferDate; }

    public Long getFromWarehouseId() { return fromWarehouseId; }
    public void setFromWarehouseId(Long fromWarehouseId) { this.fromWarehouseId = fromWarehouseId; }

    public Long getToWarehouseId() { return toWarehouseId; }
    public void setToWarehouseId(Long toWarehouseId) { this.toWarehouseId = toWarehouseId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public List<StockTransferDetail> getDetails() { return details; }
    public void setDetails(List<StockTransferDetail> details) { this.details = details; }
}