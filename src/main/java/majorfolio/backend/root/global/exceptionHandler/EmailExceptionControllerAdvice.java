/**
 * EmailExceptionControllerAdvice
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.EmailException;
import majorfolio.backend.root.global.exception.NotSchoolEmailException;
import majorfolio.backend.root.global.exception.OverlapEmailException;
import majorfolio.backend.root.global.exception.SendEmailException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 이메일관련 예외처리하는 ControllerAdvice임
 *
 * @author 김영록
 * @version 0.0.1
 */
@Slf4j
@Priority(0)
@RestControllerAdvice
public class EmailExceptionControllerAdvice {

    /**
     * 기타 이메일에 관한 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailException.class)
    public BaseErrorResponse handle_emailException(EmailException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

    /**
     * 학교 이메일이 아닐때의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotSchoolEmailException.class)
    public BaseErrorResponse handle_notSchoolEmailException(NotSchoolEmailException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

    /**
     * 이미 인증된 이메일을 사용했을때의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OverlapEmailException.class)
    public BaseErrorResponse handle_overlapEmailException(OverlapEmailException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

    /**
     * 메일을 보내는데 있어서 오류가 났을때의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SendEmailException.class)
    public BaseErrorResponse handle_sendEmailException(SendEmailException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }
}
