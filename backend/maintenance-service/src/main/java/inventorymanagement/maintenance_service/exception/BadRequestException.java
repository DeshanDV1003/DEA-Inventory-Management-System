package inventorymanagement.maintenance_service.exception;

/*
 * BadRequestException
 * Thrown when client sends invalid or incomplete data.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}