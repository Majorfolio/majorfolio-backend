package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매정보를 반환할 response
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateBuyInfoResponse {
    private Long buyInfoId;

    public static CreateBuyInfoResponse of(Long buyInfoId){
        return CreateBuyInfoResponse.builder()
                .buyInfoId(buyInfoId)
                .build();
    }
}
