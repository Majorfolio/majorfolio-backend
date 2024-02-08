package majorfolio.backend.root.global.advice;

import majorfolio.backend.root.global.exception.ExceptionResponse;
import majorfolio.backend.root.global.exception.JsonConvertException;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public BaseErrorResponse handleNotFoundException(NotFoundException e) {
        return new BaseErrorResponse(e.getExceptionStatus());
    }

    @ExceptionHandler(JsonConvertException.class)
    public ResponseEntity<ExceptionResponse> handleJsonConvertException(JsonConvertException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
