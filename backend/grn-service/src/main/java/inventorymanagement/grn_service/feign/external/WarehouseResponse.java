package inventorymanagement.grn_service.feign.external;

import lombok.*;

/*
DTO used to receive Warehouse data from the Warehouse microservice via Feign client

This response is used to validate whether a warehouse exists before creating or updating a GRN
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    //Unique warehouse identifier
    private Long id;
    //Warehouse name
    private String name;
}