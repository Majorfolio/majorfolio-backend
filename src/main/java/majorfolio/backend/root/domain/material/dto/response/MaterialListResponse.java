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
public class MaterialListResponse {
    private final List<MaterialResponse> newUpload;
    private final List<MaterialResponse> best;
    private final List<MaterialResponse> latest;

    /**
     * material들의 list 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static MaterialListResponse of(List<MaterialResponse> newUpload,
                                          List<MaterialResponse> best,
                                          List<MaterialResponse> latest){
        return MaterialListResponse.builder()
                .newUpload(newUpload)
                .best(best)
                .latest(latest)
                .build();
    }
}