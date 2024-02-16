package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UploadMaterialListResponse {
    private final List<UploadMaterialResponse> stopList;
    private final List<UploadMaterialResponse> onSaleList;

    /**
     * 업로드한 자료들의 카테고리에 따른 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public static UploadMaterialListResponse of(List<UploadMaterialResponse> stopList,
                                                List<UploadMaterialResponse> onSaleList){
        return UploadMaterialListResponse.builder()
                .stopList(stopList)
                .onSaleList(onSaleList)
                .build();
    }
}
