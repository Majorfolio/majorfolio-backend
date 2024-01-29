package majorfolio.backend.root.domain.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.content.entity.EventBanner;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentsResponse {
    private Long id;
    private String imageURL;
    private String type;
    private String backgroundColor;
    private String iconURL;

    public static ContentsResponse of(EventBanner banner) {
        return ContentsResponse.builder()
                .id(banner.getId())
                .imageURL(banner.getImageURL())
                .type(banner.getType())
                .backgroundColor(banner.getBackgroundColor())
                .iconURL(banner.getIconURL())
                .build();
    }
}
