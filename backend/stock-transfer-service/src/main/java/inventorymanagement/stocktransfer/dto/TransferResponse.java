package inventorymanagement.stocktransfer.dto;

/**
 * Simple response DTO after creating a transfer.
 */
public class TransferResponse {

    private String transferNo;
    private String status;
    private String message;

    public TransferResponse(String transferNo, String status, String message) {
        this.transferNo = transferNo;
        this.status = status;
        this.message = message;
    }

    public String getTransferNo() { return transferNo; }
    public void setTransferNo(String transferNo) { this.transferNo = transferNo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}