package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class NotSatisfiedAgreePolicyException extends UserException{
    private final ResponseStatus responseStatus;

    public NotSatisfiedAgreePolicyException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
