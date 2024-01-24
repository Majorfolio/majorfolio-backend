package majorfolio.backend.root.domain.member.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponse {
    private Boolean isMember;
    private String accessToken;
    private String refreshToken;
}
