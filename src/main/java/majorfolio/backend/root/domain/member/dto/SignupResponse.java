/**
 * SignupResponse
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 api의 응답형식 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupResponse {
    private Long memberId;
    private String accessToken;

    public static SignupResponse of(Long memberId, String accessToken){
        return SignupResponse.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .build();
    }
}
