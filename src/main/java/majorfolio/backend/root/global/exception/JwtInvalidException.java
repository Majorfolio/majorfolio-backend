package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

public class JwtInvalidException extends JwtUnauthorizedException{
    private final ResponseStatus responseStatus;


    public JwtInvalidException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
