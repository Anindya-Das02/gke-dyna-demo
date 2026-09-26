package in.das.app.gkedemo.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionResponse {
    private final String errorCode;
    private final String message;
    private final String description;
}
