package majorfolio.backend.root.global.exception;


import majorfolio.backend.root.global.response.status.ResponseStatus;

public class OverlapNicknameException extends UserException {
    private final ResponseStatus responseStatus;

    public OverlapNicknameException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
