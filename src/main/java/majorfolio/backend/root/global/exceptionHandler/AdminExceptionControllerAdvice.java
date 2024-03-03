package majorfolio.backend.root.global.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.AdminException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.URL_NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class AdminExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AdminException.class)
    public BaseErrorResponse handle_adminException(AdminException e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
