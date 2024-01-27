package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.domain.member.dto.EmailRequest;
import majorfolio.backend.root.global.response.status.ResponseStatus;

@Getter
public class EmailException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public EmailException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public EmailException(ResponseStatus responseStatus, String message){
        super(message);
        this.responseStatus = responseStatus;
    }
}
