/**
 * OverlapEmailException
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
 * 중복된 이메일일때 예외클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class OverlapEmailException extends EmailException{
    private final ResponseStatus responseStatus;

    public OverlapEmailException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
