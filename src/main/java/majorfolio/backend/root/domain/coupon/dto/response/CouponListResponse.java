package majorfolio.backend.root.domain.coupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponListResponse {
    private List<CouponResponse> couponResponses;

    public static CouponListResponse of(List<CouponResponse> couponResponse){
        return CouponListResponse.builder()
                .couponResponses(couponResponse)
                .build();
    }
}
