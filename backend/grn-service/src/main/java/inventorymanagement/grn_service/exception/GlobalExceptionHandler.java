package inventorymanagement.grn_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(404)
                        .error("NOT_FOUND")
                        .message(ex.getMessage())
                        .path(req.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .error("BAD_REQUEST")
                        .message(ex.getMessage())
                        .path(req.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .error("VALIDATION_ERROR")
                        .message(msg)
                        .path(req.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(500)
                        .error("INTERNAL_SERVER_ERROR")
                        .message(ex.getMessage())
                        .path(req.getRequestURI())
                        .build()
        );
    }
}