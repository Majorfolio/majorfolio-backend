/**
 * JwtExpiredException
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 토큰 만료시 예외클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class JwtExpiredException extends JwtUnauthorizedException{

    private final ResponseStatus responseStatus;
    public JwtExpiredException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
