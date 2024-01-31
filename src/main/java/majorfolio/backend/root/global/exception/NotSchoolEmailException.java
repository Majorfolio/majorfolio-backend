/**
 * NotSchoolEmailException
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
 * 학교 이메일이 아닐때 예외 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class NotSchoolEmailException extends EmailException{
    private final ResponseStatus responseStatus;

    public NotSchoolEmailException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
