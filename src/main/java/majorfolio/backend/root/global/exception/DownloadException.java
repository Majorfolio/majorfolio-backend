package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

public class DownloadException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public DownloadException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public DownloadException(ResponseStatus responseStatus, String message){
        super(message);
        this.responseStatus = responseStatus;
    }
}
