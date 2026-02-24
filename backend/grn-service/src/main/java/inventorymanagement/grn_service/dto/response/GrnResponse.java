package inventorymanagement.grn_service.dto.response;

import inventorymanagement.grn_service.enums.GrnStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GrnResponse {
    private String grnNumber;
    private String poNumber;
    private Long companyId;
    private Long departmentId;
    private Long warehouseId;
    private Long supplierId;
    private LocalDate date;
    private GrnStatus status;

    private BigDecimal totalGrossAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalNetAmount;

    private List<GrnItemResponse> items;
}