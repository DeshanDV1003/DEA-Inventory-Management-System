package inventorymanagement.grn_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/* Request DTO for creating a new GRN

This object contains all necessary information required to record received goods from a supplier and update inventory,
Validation annotations ensure required fields are provided before processing the request.*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGrnRequest {

    // Company identifier where the GRN belongs
    @NotNull (message = "Company ID is required")
    private Long companyId;

    //Department identifier within the company
    @NotNull (message = "Department ID is required")
    private Long departmentId;

    //Warehouse where goods are received
    @NotNull (message = "Warehouse ID is required")
    private Long warehouseId;

    //Supplier identifier from whom goods are received
    @NotNull (message = "Supplier ID is required")
    private Long supplierId;

    //Purchase order number associated with this GRN
    @NotBlank (message = "PO number is required")
    private String poNumber;

    //Date when goods were received
    @NotNull (message = "GRN date is required")
    private LocalDate date;

    //Username or identifier of the user creating the GRN
    @NotBlank (message = "CreatedBy is required")
    private String createdBy;

    //List of items received in this GRN as well as each item must pass validation
    @Valid
    @NotNull (message = "GRN items list cannot be null")
    private List<GrnItemRequest> items;
}
