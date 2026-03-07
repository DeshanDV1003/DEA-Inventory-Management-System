package inventorymanagement.grn_service.controller;

import inventorymanagement.grn_service.dto.request.CreateGrnRequest;
import inventorymanagement.grn_service.dto.request.UpdateGrnRequest;
import inventorymanagement.grn_service.dto.response.GrnResponse;
import inventorymanagement.grn_service.service.GrnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*REST Controller for handling Goods Received Note (GRN) operations.
*This controller exposes APIs to:
* create a new GRN,
* Retrieve a GRN by GRN number,
* Filter GRNs by company / warehouse,
* Update an existing GRN,
* Cancel a GRN,
*
* Base URL: /api/v1/grns
*  */

@RestController
@RequestMapping("/api/v1/grns")
@RequiredArgsConstructor
@CrossOrigin
public class GrnController {

    private final GrnService grnService;

    // Create GRN, validates input and records received goods and also updates stock quantities
    @PostMapping
    public GrnResponse create(@Valid @RequestBody CreateGrnRequest req) {
        return grnService.create(req);
    }

    // Get by GRN Number(retrieve a GRN by its unique GRN number.)
    @GetMapping("/{grnNumber}")
    public GrnResponse get(@PathVariable String grnNumber) {
        return grnService.getByGrnNumber(grnNumber);
    }

    // Filter GRNs based on optional parameters: companyId, warehouseId
    @GetMapping
    public List<GrnResponse> filter(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long warehouseId
    ) {
        return grnService.filter(companyId, warehouseId);
    }

    // Edit GRN(update an existing GRN, recalculates totals and adjusts stock differences)
    @PutMapping("/{grnNumber}")
    public GrnResponse update(@PathVariable String grnNumber, @Valid @RequestBody UpdateGrnRequest req) {
        return grnService.update(grnNumber, req);
    }

    // Cancel GRN, reverses previously added stock quantities
    @PutMapping("/{grnNumber}/cancel")
    public GrnResponse cancel(@PathVariable String grnNumber, @RequestParam String cancelledBy) {
        return grnService.cancel(grnNumber, cancelledBy);
    }


}
