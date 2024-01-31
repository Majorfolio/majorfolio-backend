package majorfolio.backend.root.domain.content.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Banner들의 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentsListResponse {

    private List<ContentsResponse> contentsList;

    /**
     * Banner응답 리스트들를 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static ContentsListResponse of(List<ContentsResponse> contentsList) {
        return ContentsListResponse.builder()
                .contentsList(contentsList)
                .build();
    }
}
