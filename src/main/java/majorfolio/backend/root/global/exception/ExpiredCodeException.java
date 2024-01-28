/**
 * ExpiredCodeException
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
 * 코드 인증 시간이 지났을때 발생하는 예외 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public class ExpiredCodeException extends EmailException{
    private final ResponseStatus responseStatus;


    public ExpiredCodeException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
