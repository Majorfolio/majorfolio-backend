/**
 * EmailCodeRequest
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 인증 코드 대조 API 에서 요청 객체
 *
 * @author 김영록
 * @version 0.0.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailCodeRequest {
    private Long emailId;
    private String code;
}
