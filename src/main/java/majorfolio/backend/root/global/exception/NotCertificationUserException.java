/**
 * NotCertificationUserException
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 이메일 인증 사용자가 아닐때 예외처리 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class NotCertificationUserException extends UserException{
    private final ResponseStatus responseStatus;

    public NotCertificationUserException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
