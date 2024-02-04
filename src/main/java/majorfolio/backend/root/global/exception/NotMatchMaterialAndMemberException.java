package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class NotMatchMaterialAndMemberException extends MaterialException{
    private final ResponseStatus responseStatus;


    public NotMatchMaterialAndMemberException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
