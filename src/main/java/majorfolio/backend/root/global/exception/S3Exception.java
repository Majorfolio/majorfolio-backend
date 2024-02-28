package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class S3Exception extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public S3Exception(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public S3Exception(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
