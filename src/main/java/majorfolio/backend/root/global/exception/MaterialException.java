/**
 * MaterialException
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import lombok.Getter;
import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 과제 관련 예외 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class MaterialException extends RuntimeException{
    private final ResponseStatus responseStatus;


    public MaterialException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
    public MaterialException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
