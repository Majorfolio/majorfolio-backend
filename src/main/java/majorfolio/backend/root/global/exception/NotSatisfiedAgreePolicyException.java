/**
 * NotSatisfiedAgreePolicyException
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
 * 필수 동의 사항 미동의 시 예외 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class NotSatisfiedAgreePolicyException extends UserException{
    private final ResponseStatus responseStatus;

    public NotSatisfiedAgreePolicyException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
