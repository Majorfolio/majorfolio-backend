package majorfolio.backend.root.domain.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostNoticeResponse {
    private Long noticeId;

    public static PostNoticeResponse of(
            Long noticeId
    ){
        return PostNoticeResponse.builder()
                .noticeId(noticeId)
                .build();
    }
}
