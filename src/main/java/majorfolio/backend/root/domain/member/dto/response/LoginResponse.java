/**
 * LoginResponse
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.dto.response;

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
    private Boolean isWriteMemberDetailInfo; // 상세 정보(학교명, 학번 등 기입했는지 여부)
    private Long memberId;
    private Long emailId;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(Boolean isMember, Boolean isWriteMemberDetailInfo,
                                   Long memberId, Long emailId, String accessToken,
                                   String refreshToken){
        return LoginResponse.builder()
                .isMember(isMember)
                .isWriteMemberDetailInfo(isWriteMemberDetailInfo)
                .memberId(memberId)
                .emailId(emailId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
