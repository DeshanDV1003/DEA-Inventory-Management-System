package inventorymanagement.stocktransfer.controller;

import inventorymanagement.stocktransfer.dto.CreateTransferRequest;
import inventorymanagement.stocktransfer.dto.TransferResponse;
import inventorymanagement.stocktransfer.entity.StockTransferDetail;
import inventorymanagement.stocktransfer.entity.StockTransferHeader;
import inventorymanagement.stocktransfer.service.StockTransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Stock Transfer Controller
 * -------------------------
 * This controller exposes REST APIs to:
 * 1) Create a stock transfer (Header + Details)
 * 2) View transfers (all / by transfer number)
 * 3) View transfer detail lines
 *
 * Note:
 * - Controller should NOT contain business logic.
 * - All business rules should be in the Service layer.
 */
@RestController
@RequestMapping("/api/v1/transfers")
public class StockTransferController {

    private final StockTransferService service;

    // Constructor injection (recommended - easy to test, clean)
    public StockTransferController(StockTransferService service) {
        this.service = service;
    }

    /**
     * Create a new stock transfer.
     * - Accepts a request body (DTO)
     * - Calls service to create header + detail lines
     * - Returns transfer number + status
     */
    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(
            @Valid @RequestBody CreateTransferRequest request
    ) {
        StockTransferHeader header = service.createTransfer(request);

        TransferResponse response = new TransferResponse(
                header.getTransferNo(),
                header.getStatus(),
                "Transfer created successfully"
        );

        // 201 CREATED is correct for new record creation
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all transfer headers.
     * Used for listing transfers in UI.
     */
    @GetMapping
    public ResponseEntity<List<StockTransferHeader>> getAllTransfers() {
        return ResponseEntity.ok(service.getAllHeaders());
    }

    /**
     * Get one transfer header by transfer number.
     * Example: GET /api/transfers/TR-12345
     */
    @GetMapping("/{transferNo}")
    public ResponseEntity<StockTransferHeader> getTransferByNo(
            @PathVariable String transferNo
    ) {
        return ResponseEntity.ok(service.getByTransferNo(transferNo));
    }

    /**
     * Get all detail lines for a transfer number.
     * Example: GET /api/transfers/TR-12345/details
     */
    @GetMapping("/{transferNo}/details")
    public ResponseEntity<List<StockTransferDetail>> getTransferDetails(
            @PathVariable String transferNo
    ) {
        return ResponseEntity.ok(service.getDetailsByTransferNo(transferNo));
    }
}
