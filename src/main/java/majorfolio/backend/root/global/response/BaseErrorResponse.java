package majorfolio.backend.root.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"code", "status", "message", "timestamp"})
public class BaseErrorResponse implements ResponseStatus{
    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public BaseErrorResponse(ResponseStatus status) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public BaseErrorResponse(ResponseStatus status, String message) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
