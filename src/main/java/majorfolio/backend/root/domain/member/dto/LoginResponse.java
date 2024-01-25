/**
 * LoginResponse
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import lombok.*;

/**
 * /member/login으로 요청시에 응답 반환해줄 데이터 정의
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private Boolean isMember;
    private String accessToken;
    private String refreshToken;
}
