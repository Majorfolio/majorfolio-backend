package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class JwtUnauthorizedException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public JwtUnauthorizedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
