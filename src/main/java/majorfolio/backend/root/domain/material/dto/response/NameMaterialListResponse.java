package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 이 수업의 모든 자료애서의 material들의 list 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class NameMaterialListResponse {
    private int page;
    private final List<NameMaterialResponse> newUpload;
    private boolean isEnd;

    /**
     * material들의 list 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static NameMaterialListResponse of(int page, List<NameMaterialResponse> newUpload, boolean isEnd){
        return NameMaterialListResponse.builder()
                .page(page)
                .newUpload(newUpload)
                .isEnd(isEnd)
                .build();
    }
}