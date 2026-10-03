package in.das.app.gkedemo.exceptions;

import in.das.app.gkedemo.models.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

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
                .message("Unexpected error occurred")
                .description("an exception occurred")
                .build();
        return ResponseEntity.status(500).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        logMessage(ex);
        var response = ExceptionResponse.builder()
                .errorCode("ERR_105")
                .message(ex.getMessage())
                .description(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                .build();
        return ResponseEntity.status(statusCode).headers(headers).body(response);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String details = ex.getBindingResult().getFieldErrors().stream()
//                .map(f -> f.getField() + ": " + f.getDefaultMessage())
//                .collect(Collectors.joining("; "));
//
//        var response = ExceptionResponse.builder()
//                .errorCode("ERR_400")
//                .message("Validation failed")
//                .description(details)
//                .build();
//        return ResponseEntity.badRequest().body(response);
//    }

    private void logMessage(Exception e) {
        log.error("returning error response, " + e.getMessage(), e);
    }
}
