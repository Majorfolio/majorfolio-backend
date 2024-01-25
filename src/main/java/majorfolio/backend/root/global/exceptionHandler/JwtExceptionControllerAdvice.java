/**
 * JwtExceptionControllerAdvice
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.JwtUnsupportedTokenTypeException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Jwt관련 예외처리하는 ControllerAdvice임
 * @author 김영록
 * @version 0.0.1
 */
@Slf4j
@Priority(0)
@RestControllerAdvice
@RequiredArgsConstructor
public class JwtExceptionControllerAdvice {


    /**
     * 지원하지 않는 토큰 타입일때의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtUnsupportedTokenTypeException.class)
    public BaseErrorResponse handle_jwtUnauthorizedException(JwtUnsupportedTokenTypeException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

    /**
     * 만료된 토큰일때 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtExpiredException.class)
    public BaseErrorResponse handle_jwtExpiredException(JwtExpiredException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

    /**
     * 유효하지 않은 토큰(형식 or 값이 잘못되었을떄)의 예외처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtInvalidException.class)
    public BaseErrorResponse handle_jwtInvalidException(JwtInvalidException e){
        return new BaseErrorResponse(e.getResponseStatus());
    }

}
