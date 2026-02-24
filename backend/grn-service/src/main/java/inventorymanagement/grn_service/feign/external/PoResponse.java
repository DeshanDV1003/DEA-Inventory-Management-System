package inventorymanagement.grn_service.feign.external;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PoResponse {
    private String poNumber;
    private String status; // e.g. APPROVED / OPEN / CLOSED
}