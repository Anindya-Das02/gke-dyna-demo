package in.das.app.gkedemo.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String message;
    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
