package inventorymanagement.grn_service.enums;

/*Enum representing the possible statuses of a GRN

DRAFT - GRN is created but not finalized. Stock is not updated,
COMPLETED - GRN is finalized and stock quantities are updated,
CANCELLED - GRN is cancelled and stock adjustments are reversed
 */

public enum GrnStatus {

    //Initial state before confirmation
    DRAFT,

    //GRN is finalized and stock has been updated
    COMPLETED,

    //GRN is cancelled and stock changes are reversed
    CANCELLED
}