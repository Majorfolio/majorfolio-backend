/**
 * MemberException
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;

import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 회원정보를 다루는데 있어서의 예외를 정의해놓은 클래스
 *
 * @author 김영록
 * @version 0.0.1
 */
public class MemberException extends RuntimeException{
    private final ResponseStatus responseStatus;

    public MemberException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
    public MemberException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
