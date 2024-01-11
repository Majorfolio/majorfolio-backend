package majorfolio.backend.global.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import psychat.backend.global.exception.ExceptionResponse;
import psychat.backend.global.exception.JsonConvertException;
import psychat.backend.global.exception.NotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonConvertException.class)
    public ResponseEntity<ExceptionResponse> handleJsonConvertException(JsonConvertException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
