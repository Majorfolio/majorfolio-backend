/**
 * RemakeTokenResponse
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
 * 토큰 재발급 api 요청시 그에 대한 응답을 정의해놓은 클래스
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RemakeTokenResponse {
    private String accessToken;
    private String refreshToken;

    /**
     * 빌더 패턴으로 객체를 생성하기 위한 메서드
     * @param accessToken
     * @param refreshToken
     * @return
     */
    public static RemakeTokenResponse of(String accessToken, String refreshToken){
        return RemakeTokenResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
