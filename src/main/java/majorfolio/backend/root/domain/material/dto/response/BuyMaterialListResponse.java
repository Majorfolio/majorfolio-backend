package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BuyMaterialListResponse {
    private final List<BuyMaterialResponse> beforePay;
    private final List<BuyMaterialResponse> afterPay;
    private final List<BuyMaterialResponse> downloadComplete;

    /**
     * 구매한 자료들의 카테고리에 따른 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public static BuyMaterialListResponse of(List<BuyMaterialResponse> beforePay,
                                             List<BuyMaterialResponse> afterPay,
                                             List<BuyMaterialResponse> downloadComplete){
        return BuyMaterialListResponse.builder()
                .beforePay(beforePay)
                .afterPay(afterPay)
                .downloadComplete(downloadComplete)
                .build();
    }
}
