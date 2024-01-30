/**
 * SendEmailException
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.exception;


import majorfolio.backend.root.global.response.status.ResponseStatus;

/**
 * 이메일 전송시 예외 사항 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public class SendEmailException extends EmailException {
    private final ResponseStatus responseStatus;

    public SendEmailException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
