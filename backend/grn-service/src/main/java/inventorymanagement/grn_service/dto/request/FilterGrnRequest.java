package inventorymanagement.grn_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterGrnRequest {

    private Long companyId;

    private Long departmentId;

    private Long warehouseId;
}
