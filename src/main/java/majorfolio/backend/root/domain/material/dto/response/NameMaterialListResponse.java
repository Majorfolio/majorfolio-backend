package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * material들의 list 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class NameMaterialListResponse {
    private int page;
    private final List<NameMaterialResponse> newUpload;

    /**
     * material들의 list 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static NameMaterialListResponse of(int page, List<NameMaterialResponse> newUpload){
        return NameMaterialListResponse.builder()
                .page(page)
                .newUpload(newUpload)
                .build();
    }
}