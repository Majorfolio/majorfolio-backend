package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class JwtUnsupportedTokenTypeException extends JwtUnauthorizedException{
    private final ResponseStatus responseStatus;

    public JwtUnsupportedTokenTypeException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
