/**
 * GlobalExceptionHandler
 *
 * A centralized utility to catch and format errors across the microservice.
 *
 * Purpose:
 * - Intercepts 'MethodArgumentNotValidException' (Validation errors).
 * - Intercepts 'RuntimeException' (Resource not found/Business errors).
 *
 * Response Format:
 * - Returns a Map of field names and error messages with appropriate HTTP
 *   status codes (400 Bad Request or 404 Not Found).
 *
 * Architecture:
 * - Uses @RestControllerAdvice to provide global error handling for all Controllers.
 */

package inventorymanagement.purchase_order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Validation Errors (e.g., negative quantity, missing fields)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Handle Resource Not Found (e.g., trying to update ID that doesn't exist)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}