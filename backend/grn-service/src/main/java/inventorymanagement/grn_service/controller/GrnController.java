package inventorymanagement.grn_service.controller;

import inventorymanagement.grn_service.dto.request.CreateGrnRequest;
import inventorymanagement.grn_service.dto.request.UpdateGrnRequest;
import inventorymanagement.grn_service.dto.response.GrnResponse;
import inventorymanagement.grn_service.service.GrnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grns")
@RequiredArgsConstructor
@CrossOrigin
public class GrnController {

    private final GrnService grnService;

    // Create GRN
    @PostMapping
    public GrnResponse create(@Valid @RequestBody CreateGrnRequest req) {
        return grnService.create(req);
    }

    // Get by GRN Number
    @GetMapping("/{grnNumber}")
    public GrnResponse get(@PathVariable String grnNumber) {
        return grnService.getByGrnNumber(grnNumber);
    }

    // Filter
    @GetMapping
    public List<GrnResponse> filter(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long warehouseId
    ) {
        return grnService.filter(companyId, departmentId, warehouseId);
    }

    // Edit GRN
    @PutMapping("/{grnNumber}")
    public GrnResponse update(@PathVariable String grnNumber, @Valid @RequestBody UpdateGrnRequest req) {
        return grnService.update(grnNumber, req);
    }

    // Cancel GRN
    @PutMapping("/{grnNumber}/cancel")
    public GrnResponse cancel(@PathVariable String grnNumber, @RequestParam String cancelledBy) {
        return grnService.cancel(grnNumber, cancelledBy);
    }


}
