/**
 * UserExceptionControllerAdvice
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.NotCertificationUserException;
import majorfolio.backend.root.global.exception.NotSatisfiedAgreePolicyException;
import majorfolio.backend.root.global.exception.OverlapNicknameException;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * User관련 예외 처리하는 ControllerAdvice임
 *
 * @author 김영록
 * @version 0.0.1
 */
@RestControllerAdvice
@Slf4j
@Priority(0)
public class UserExceptionControllerAdvice {

    /**
     * 기타 유저 관련 예외사항 처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handle_UserException(UserException e) {
        log.error("[handle_UserException]", e);
        return new BaseErrorResponse(INVALID_USER_VALUE, e.getMessage());
    }

    /**
     * 필수동의사항 미동의시 예외사항 처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotSatisfiedAgreePolicyException.class)
    public BaseErrorResponse handle_NotSatisfiedAgreePolicyException(NotSatisfiedAgreePolicyException e){
        return new BaseErrorResponse(NOT_SATISFIED_AGREE_POLICY, e.getMessage());
    }

    /**
     * 닉네임이 겹쳤을때 예외사항 처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OverlapNicknameException.class)
    public BaseErrorResponse handle_OverlapNicknameException(OverlapNicknameException e){
        return new BaseErrorResponse(OVERLAP_NICKNAME, e.getMessage());
    }

    /**
     * 이메일 인증한 사용자가 아닐때 예외사항 처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotCertificationUserException.class)
    public BaseErrorResponse handle_NotCertificationUserException(NotCertificationUserException e){
        return new BaseErrorResponse(NOT_CERTIFICATION_USER, e.getMessage());
    }
}
