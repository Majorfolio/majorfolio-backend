/**
 * SignupResponse
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입시 그에 대한 응답을 정의해놓은 클래스
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupResponse {
    private String accessToken;

    /**
     * 빌더 패턴으로 객체를 생성하기 위한 메서드
     * @param accessToken
     * @return
     */
    public static SignupResponse of(String accessToken){
        return SignupResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }
}
