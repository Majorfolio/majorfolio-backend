package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.S3Exception;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Priority(0)
public class S3ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(S3Exception.class)
    public BaseErrorResponse handle_S3Exception_BAD_REQUEST(S3Exception e) {
        log.error("[handle_S3Exception]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
