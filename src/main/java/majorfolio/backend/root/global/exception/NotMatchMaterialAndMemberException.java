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
 * 과제 주인과 사용자의 memberId가 같지 않을 때 발생하는 예외 클래스 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Getter
public class NotMatchMaterialAndMemberException extends MaterialException{
    private final ResponseStatus responseStatus;


    public NotMatchMaterialAndMemberException(ResponseStatus responseStatus) {
        super(responseStatus);
        this.responseStatus = responseStatus;
    }
}
