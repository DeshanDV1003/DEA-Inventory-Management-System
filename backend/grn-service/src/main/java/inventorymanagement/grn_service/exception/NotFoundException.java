package inventorymanagement.grn_service.exception;

/*Custom exception thrown when a requested resource cannot be found in the system

This typically results in HTTP 404 (Not Found)
ex: GRN not found, Warehouse not found, PO not found
 */

public class NotFoundException extends RuntimeException {

    //Creates a new NotFoundException with a specific message
    //@param message detailed error message
    public NotFoundException(String message) {
        super(message);
    }
}