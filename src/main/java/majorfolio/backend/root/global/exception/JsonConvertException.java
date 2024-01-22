package majorfolio.backend.root.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JsonConvertException extends RuntimeException {

    private final String message;
}
