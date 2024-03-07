package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

public class NoAuthUserException extends UserException{
    private final ResponseStatus responseStatus;

    public NoAuthUserException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
