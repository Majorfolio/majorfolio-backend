package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShowNoticeDetailResponse {
    private String title;
    private String link;

    public static ShowNoticeDetailResponse of(
            String title,
            String link
    ){
        return ShowNoticeDetailResponse.builder()
                .title(title)
                .link(link)
                .build();
    }
}
