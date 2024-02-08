package majorfolio.backend.root.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public NotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public NotFoundException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
