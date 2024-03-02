package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class FileException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public FileException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public FileException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
