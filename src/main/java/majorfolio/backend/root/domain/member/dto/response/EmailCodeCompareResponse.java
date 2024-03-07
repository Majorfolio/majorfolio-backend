package majorfolio.backend.root.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailCodeCompareResponse {
    private String accessToken;

    public static EmailCodeCompareResponse of(
            String accessToken
    ){
        return EmailCodeCompareResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
