<<<<<<<< HEAD:src/main/java/majorfolio/backend/root/global/exceptionHandler/ApiExceptionHandler.java
package majorfolio.backend.root.global.exceptionHandler;
========
package majorfolio.backend.root.global.advice;
>>>>>>>> ba88a3e (feat: domain -> entity로 패키지 구조 변경 && assignment, analytics entity 생성):src/main/java/majorfolio/backend/root/global/advice/ApiExceptionHandler.java

import majorfolio.backend.root.global.exception.ExceptionResponse;
import majorfolio.backend.root.global.exception.JsonConvertException;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
