package inventorymanagement.grn_service.feign.external;

import lombok.*;

/*DTO used to receive Purchase Order (PO) data from the external PO microservice via Feign client

This response is used during GRN creation to validate whether the PO exists and is valid
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoResponse {

    //Unique Purchase Order number
    private String poNumber;
    //Current status of the PO
    private String status; // eg: Approved / Open / Closed
}