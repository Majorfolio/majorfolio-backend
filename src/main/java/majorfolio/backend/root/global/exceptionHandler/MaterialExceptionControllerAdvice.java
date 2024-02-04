/**
 * MaterialExceptionControllerAdvice
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.NotMatchMaterialAndMemberException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_MATCH_MATERIAL_AND_MEMBER;

/**
 * 과제 관련 예외 발생시 처리 로직 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Slf4j
@Priority(0)
@RestControllerAdvice
public class MaterialExceptionControllerAdvice {
    /**
     * NotMatchMaterialAndMemberException예외 발생시 처리 로직 정의
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchMaterialAndMemberException.class)
    public BaseErrorResponse handle_notMatchMaterialAndMemberException(NotMatchMaterialAndMemberException e){
        return new BaseErrorResponse(NOT_MATCH_MATERIAL_AND_MEMBER, e.getMessage());
    }
}
