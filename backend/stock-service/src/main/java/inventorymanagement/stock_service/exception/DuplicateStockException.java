package inventorymanagement.stock_service.exception;

public class DuplicateStockException extends  RuntimeException{
    public DuplicateStockException(String message) {
        super(message);
    }
}
