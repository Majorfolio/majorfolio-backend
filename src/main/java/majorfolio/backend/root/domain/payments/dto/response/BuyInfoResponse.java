package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.payments.entity.BuyInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BuyInfo 리스폰스 형태 정의
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuyInfoResponse {
    private List<MaterialNameResponse> materialNameResponseList;
    private int totalPrice;
    private LocalDate createDate;
    private String code;

    public static BuyInfoResponse of(List<MaterialNameResponse> materialNameResponseList,
                                     BuyInfo buyInfo) {
        return BuyInfoResponse.builder()
                .materialNameResponseList(materialNameResponseList)
                .totalPrice(buyInfo.getPrice())
                .createDate(buyInfo.getCreatedAt().toLocalDate())
                .code(buyInfo.getCode())
                .build();
    }
}
