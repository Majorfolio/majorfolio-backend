package majorfolio.backend.root.global.exception;


import majorfolio.backend.root.global.response.status.ResponseStatus;

public class SendEmailException extends EmailException {
    private final ResponseStatus responseStatus;

    public SendEmailException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
