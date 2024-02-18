package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.NotDownloadAuthorizationException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_DOWNLOAD_AUTHORIZATION;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.URL_NOT_FOUND;

/**
 * 다운로드 관련 exception handler
 */
@Slf4j
@Priority(0)
@RestControllerAdvice
public class DownloadExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotDownloadAuthorizationException.class)
    public BaseErrorResponse handle_NotDownloadAuthorizationException(Exception e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(NOT_DOWNLOAD_AUTHORIZATION);
    }
}
