package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShowEventDetailResponse {
    private String title;
    private String link;

    public static ShowEventDetailResponse of(
            String title,
            String link
    ){
        return ShowEventDetailResponse.builder()
                .title(title)
                .link(link)
                .build();
    }
}
