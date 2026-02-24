package inventorymanagement.grn_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UpdateGrnRequest {

    @NotNull
    private Long companyId;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long supplierId;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String modifiedBy;

    @Valid
    @NotNull
    private List<GrnItemRequest> items;
}
