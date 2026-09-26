package in.das.app.gkedemo.exceptions;

import in.das.app.gkedemo.models.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionResponseHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logMessage(ex);
        var response = ExceptionResponse.builder()
                .errorCode("ERR_101")
                .message(ex.getMessage())
                .description("an exception occurred")
                .build();
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ExceptionResponse> handleExternalServiceException(ExternalServiceException ex) {
        logMessage(ex);
        var response = ExceptionResponse.builder()
                .errorCode("ERR_102")
                .message(ex.getMessage())
                .description("an exception occurred")
                .build();
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        logMessage(ex);
        var response = ExceptionResponse.builder()
                .errorCode("ERR_100")
                .message(ex.getMessage())
                .description("an exception occurred")
                .build();
        return ResponseEntity.status(500).body(response);
    }

    private void logMessage(Exception e) {
        log.error("returning error response, " + e.getMessage(), e);
    }
}
