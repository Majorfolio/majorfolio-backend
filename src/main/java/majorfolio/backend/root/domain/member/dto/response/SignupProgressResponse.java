package majorfolio.backend.root.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignupProgressResponse {
    private Long kakaoId;
    private Long emailId;
    private Long memberId;

    public static SignupProgressResponse of(Long kakaoId, Long emailId, Long memberId){
        return SignupProgressResponse.builder()
                .kakaoId(kakaoId)
                .emailId(emailId)
                .memberId(memberId)
                .build();
    }
}
