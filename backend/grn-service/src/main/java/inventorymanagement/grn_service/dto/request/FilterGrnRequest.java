package inventorymanagement.grn_service.dto.request;

import lombok.*;

/* Request DTO used for filtering GRN
All fields are optional. If a field is provided, filtering will be applied based on that value

This DTO allows flexible searching by company, department, warehouse
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterGrnRequest {

    //filter by company id
    private Long companyId;

    //filter by warehouse id
    private Long warehouseId;
}
