package majorfolio.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    private String message;

    public static ExceptionResponse of(String message) {
        return ExceptionResponse.builder()
                .message(message)
                .build();
    }
}
