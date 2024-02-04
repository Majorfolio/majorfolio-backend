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

@Slf4j
@Priority(0)
@RestControllerAdvice
public class MaterialExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchMaterialAndMemberException.class)
    public BaseErrorResponse handle_notMatchMaterialAndMemberException(NotMatchMaterialAndMemberException e){
        return new BaseErrorResponse(NOT_MATCH_MATERIAL_AND_MEMBER, e.getMessage());
    }
}
