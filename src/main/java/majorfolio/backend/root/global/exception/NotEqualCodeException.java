/**
 * NotEqualCodeException
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 이메일 인증 코드가 다를때에 나타나는 예외클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public class NotEqualCodeException extends EmailException{
    private final ResponseStatus responseStatus;

    public NotEqualCodeException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
