package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * Download시 권한이 없을 때 뱉어주는 예외처리
 */
public class NotDownloadAuthorizationException extends DownloadException{
    private final ResponseStatus responseStatus;

    public NotDownloadAuthorizationException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
