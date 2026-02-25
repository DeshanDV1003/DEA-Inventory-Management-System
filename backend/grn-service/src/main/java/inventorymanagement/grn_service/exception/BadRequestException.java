package inventorymanagement.grn_service.exception;

/*Custom exception thrown when the client sends invalid or incorrect request data
This results in HTTP 400 (Bad Request) response
 */

public class BadRequestException extends RuntimeException {

    //Constructs a new BadRequestException with the specified error message
    //@param message detailed error message
    public BadRequestException(String message) {
        super(message);
    }
}