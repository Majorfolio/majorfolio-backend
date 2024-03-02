package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.FileException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class FileExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileException.class)
    public BaseErrorResponse handle_FileException(FileException e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
