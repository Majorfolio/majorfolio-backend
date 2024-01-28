/**
 * JwtInvalidException
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 유효하지 않은 토큰일때의 예외클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public class JwtInvalidException extends JwtUnauthorizedException{
    private final ResponseStatus responseStatus;


    public JwtInvalidException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
