package inventorymanagement.grn_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/* Request DTO used to update an existing GRN
This object contains updated header information and a new list of GRN items

During update:
Totals will be recalculated,
Stock differences will be adjusted,
Existing details will be replaced
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateGrnRequest {

    // Company identifier
    @NotNull (message = "Company ID is required")
    private Long companyId;

    // Department identifier
    @NotNull (message = "Department ID is required")
    private Long departmentId;

    //Warehouse identifier where goods are stored
    @NotNull (message = "Warehouse ID is required")
    private Long warehouseId;

    //Supplier identifier
    @NotNull (message = "Supplier ID is required")
    private Long supplierId;

    //Updated GRN date
    @NotNull (message = "GRN date is required")
    private LocalDate date;

    //User who modified the GRN
    @NotBlank (message = "ModifiedBy is required")
    private String modifiedBy;

    //Updated list of GRN items and each item will be validated before processing
    @Valid
    @NotNull (message = "GRN items list cannot be null")
    private List<GrnItemRequest> items;
}
