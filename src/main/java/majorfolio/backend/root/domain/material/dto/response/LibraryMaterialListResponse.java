package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LibraryMaterialListResponse {
    private final List<LibraryMaterialResponse> beforePay;
    private final List<LibraryMaterialResponse> afterPay;
    private final List<LibraryMaterialResponse> downloadComplete;

    /**
     * 구매한 자료들의 카테고리에 따른 반환
     * @author 김태혁
     * @version 0.0.1
     */
    public static LibraryMaterialListResponse of(List<LibraryMaterialResponse> beforePay,
                                          List<LibraryMaterialResponse> afterPay,
                                          List<LibraryMaterialResponse> downloadComplete){
        return LibraryMaterialListResponse.builder()
                .beforePay(beforePay)
                .afterPay(afterPay)
                .downloadComplete(downloadComplete)
                .build();
    }
}
