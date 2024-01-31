package majorfolio.backend.root.domain.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.content.entity.EventBanner;

/**
 * Banner의 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
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

    /**
     * Banner의 응답 생성
     * @author 김태혁
     * @version 0.0.1
     */
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
