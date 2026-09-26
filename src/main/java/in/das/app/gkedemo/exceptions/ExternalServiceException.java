package in.das.app.gkedemo.exceptions;

import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException {
    private final String message;
    public ExternalServiceException(String message) {
        super(message);
        this.message = message;
    }
}
