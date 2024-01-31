package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 하나의 타입의 material list 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class SingleMaterialListResponse {
    private final List<MaterialResponse> materialResponseList;

    /**
     * material들의 list 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static SingleMaterialListResponse of(List<MaterialResponse> materialResponseList){
        return SingleMaterialListResponse.builder()
                .materialResponseList(materialResponseList)
                .build();
    }
}