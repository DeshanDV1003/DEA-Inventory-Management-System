package inventorymanagement.grn_service.feign.external;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class WarehouseResponse {
    private Long id;
    private String name;
}