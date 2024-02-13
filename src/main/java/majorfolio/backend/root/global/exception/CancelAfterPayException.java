/**
 * NotMatchMaterialAndMemberException
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
 * 구매취소 전에 이미 송금을 보넀을 때의 예외처리
 *
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
public class CancelAfterPayException extends PaymentsException{
    private final ResponseStatus responseStatus;


    public CancelAfterPayException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
