package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.NotSatisfiedAgreePolicyException;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.INVALID_USER_VALUE;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_SATISFIED_AGREE_POLICY;

@RestControllerAdvice
@Slf4j
@Priority(0)
public class UserExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handle_UserException(UserException e) {
        log.error("[handle_UserException]", e);
        return new BaseErrorResponse(INVALID_USER_VALUE, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotSatisfiedAgreePolicyException.class)
    public BaseErrorResponse handle_NotSatisfiedAgreePolicyException(NotSatisfiedAgreePolicyException e){
        return new BaseErrorResponse(NOT_SATISFIED_AGREE_POLICY, e.getMessage());
    }
}
