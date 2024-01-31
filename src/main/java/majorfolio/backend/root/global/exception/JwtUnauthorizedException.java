/**
 * JwtUnauthoried Exception
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * Jwt관련 예외 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class JwtUnauthorizedException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public JwtUnauthorizedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
