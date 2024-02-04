package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class MaterialException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public MaterialException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
    public MaterialException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
