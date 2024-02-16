/**
 * UserException
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
 * 결제 예외 클래스 정의
 *
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
public class PaymentsException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public PaymentsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public PaymentsException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
