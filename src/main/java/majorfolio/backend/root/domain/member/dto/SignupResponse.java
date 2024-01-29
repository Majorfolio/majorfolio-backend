package majorfolio.backend.root.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
