package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShowNoticeListResponse {
    private Long noticeId;
    private String title;

    public static ShowNoticeListResponse of(
            Long noticeId,
            String title
    ){
        return ShowNoticeListResponse.builder()
                .noticeId(noticeId)
                .title(title)
                .build();
    }
}
