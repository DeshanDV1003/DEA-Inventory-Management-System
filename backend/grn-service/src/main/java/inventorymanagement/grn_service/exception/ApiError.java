package inventorymanagement.grn_service.exception;

import lombok.*;

import java.time.LocalDateTime;

/*ApiError represents a standardized error response returned to the client when an exception occur

This ensures consistent error handling across the application

It contains:
Timestamp of the error,
HTTP status code,
Error type,
Detailed message,
Request path
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {

    //Time when the error occurred
    private LocalDateTime timestamp;
    //HTTP status code (eg: 400, 404, 500)
    private int status;
    //Short error description (eg: Bad Request, Not Found)
    private String error;
    //Detailed error message
    private String message;
    //API endpoint path where the error occurred
    private String path;
}
