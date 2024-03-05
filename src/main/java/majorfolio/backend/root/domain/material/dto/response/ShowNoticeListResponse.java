package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 공지사항 모아보기 api의 응답 객체 정의
 */
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
