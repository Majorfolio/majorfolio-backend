/**
 * MemberExceptionControllerAdvice
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.MemberException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.INVALID_USER_VALUE;

/**
 * 회원정보 관련 예외를 처리하는 advice이다.
 *
 * @author 김영록
 * @version 0.0.1
 */
@Slf4j
@RestControllerAdvice
@Priority(0)
public class MemberExceptionControllerAdvice {

    /**
     * 올바르지 않은 회원정보일때의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public BaseErrorResponse handle_memberException(MemberException e) {
        log.error("[MemberException_BadRequest]", e);
        return new BaseErrorResponse(INVALID_USER_VALUE, e.getMessage());
    }
}
