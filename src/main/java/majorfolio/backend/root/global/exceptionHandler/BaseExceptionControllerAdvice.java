package majorfolio.backend.root.global.exceptionHandler;

import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.CancelAfterPayException;
import majorfolio.backend.root.global.exception.InternalServerErrorException;
import majorfolio.backend.root.global.exception.PaymentsException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import majorfolio.backend.root.global.response.BaseResponse;
import majorfolio.backend.root.global.response.status.BaseExceptionStatus;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

@Slf4j
@RestControllerAdvice
public class BaseExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NoHandlerFoundException.class, TypeMismatchException.class})
    public BaseErrorResponse handle_BadRequest(Exception e) {
        log.error("[handle_BadRequest]", e);
        return new BaseErrorResponse(URL_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseErrorResponse handle_HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[handle_HttpRequestMethodNotSupportedException]", e);
        return new BaseErrorResponse(METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseErrorResponse handle_ConstraintViolationException(ConstraintViolationException e) {
        log.error("[handle_ConstraintViolationException]", e);
        return new BaseErrorResponse(BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public BaseErrorResponse handle_InternalServerError(InternalServerErrorException e) {
        log.error("[handle_InternalServerError]", e);
        return new BaseErrorResponse(e.getExceptionStatus());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseErrorResponse handle_RuntimeException(Exception e) {
        log.error("[handle_RuntimeException]", e);
        return new BaseErrorResponse(SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handle_MethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        return makeErrorResponse(e.getBindingResult());
    }

    @ExceptionHandler(CancelAfterPayException.class)
    public ResponseEntity<BaseErrorResponse> handleCancelAfterPayException(CancelAfterPayException ex) {
        //ㅣlog.error("[handle_CancelAfterPayException]", ex);
        BaseErrorResponse errorResponse =  new BaseErrorResponse(ex.getResponseStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getExceptionStatus().getStatus()));
    }



    @ExceptionHandler(PaymentsException.class)
    public ResponseEntity<BaseErrorResponse> handlePaymentsException(PaymentsException ex) {
        //ㅣlog.error("[handle_CancelAfterPayException]", ex);
        BaseErrorResponse errorResponse =  new BaseErrorResponse(ex.getExceptionStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getExceptionStatus().getStatus()));
    }

    /**
     * @Validation에서 에러가 났을 때 처리해주는 컨트롤러 구현
     * @param bindingResult
     * @return
     */
    private BaseErrorResponse makeErrorResponse(BindingResult bindingResult){

        String description;

        if(bindingResult.hasErrors()){
            description = bindingResult.getFieldError().getDefaultMessage();
            String bindAnnotation = bindingResult.getFieldError().getCode();

            String errorVariable = description.split(" : ")[0];
            if(bindAnnotation.equals("NotBlank")){
                if(errorVariable.equals("title")){
                   return makeResponse(NOT_BLANK_MATERIAL_TITLE);
                }
                if(errorVariable.equals("major")){
                    return makeResponse(NOT_BLANK_MATERIAL_MAJOR);
                }
                if(errorVariable.equals("semester")){
                    return makeResponse(NOT_BLANK_MATERIAL_SEMESTER);
                }
                if(errorVariable.equals("subjectName")){
                    return makeResponse(NOT_BLANK_SUBJECT_NAME);
                }
                if(errorVariable.equals("professor")){
                    return makeResponse(NOT_BLANK_PROFESSOR);
                }
                if(errorVariable.equals("description")){
                    return makeResponse(NOT_BLANK_DESCRIPTION);
                }
            }

            if(bindAnnotation.equals("NotNull")){
                if(errorVariable.equals("fullScore")){
                    return makeResponse(NOT_NULL_FULL_SCORE);
                }
                if(errorVariable.equals("score")){
                    return makeResponse(NOT_NULL_SCORE);
                }
                if(errorVariable.equals("file")){
                    return makeResponse(NOT_NULL_FILE);
                }

            }

            if(bindAnnotation.equals("Pattern")){
                if(errorVariable.equals("grade")){
                    return makeResponse(GRADE_PATTERN_ERROR);
                }
                if(errorVariable.equals("semester")){
                    return makeResponse(SEMESTER_PATTERN_ERROR);
                }
                if(errorVariable.equals("phoneNumber")){
                    return makeResponse(INVALID_USER_PHONE_NUMBER);
                }
            }

            if(bindAnnotation.equals("Size")){
                if(errorVariable.equals("description")){
                    return makeResponse(TOO_MANY_DESCRIPTION);
                }
            }
        }
        return new BaseErrorResponse(BAD_REQUEST);
    }

    private BaseErrorResponse makeResponse(BaseExceptionStatus baseExceptionStatus){
        int code;
        int status;
        String message;

        code = baseExceptionStatus.getCode();
        status = baseExceptionStatus.getStatus();
        message = baseExceptionStatus.getMessage();

        return new BaseErrorResponse(code, status, message);
    }
}
