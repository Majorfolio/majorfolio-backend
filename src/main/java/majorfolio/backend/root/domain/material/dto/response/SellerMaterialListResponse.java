package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 판매자의 material들의 list 응답 형태 정의
 * @author 김태혁
 * @version 0.0.1
 */
@Getter
@Builder
public class SellerMaterialListResponse {
    private int page;
    private final List<SellerMaterialResponse> sellList;

    /**
     * material들의 list 응답 형태 생성
     * @author 김태혁
     * @version 0.0.1
     */
    public static SellerMaterialListResponse of(int page, List<SellerMaterialResponse> sellList){
        return SellerMaterialListResponse.builder()
                .page(page)
                .sellList(sellList)
                .build();
    }
}