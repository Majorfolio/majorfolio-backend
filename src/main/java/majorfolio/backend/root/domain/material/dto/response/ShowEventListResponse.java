package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 이벤트 모아보기 api의 응답객체 정의
 */
@Getter
@Builder
public class ShowEventListResponse {
    private Long eventId;
    private String title;

    public static ShowEventListResponse of(
            Long eventId,
            String title
    ){
        return ShowEventListResponse.builder()
                .eventId(eventId)
                .title(title)
                .build();
    }
}
