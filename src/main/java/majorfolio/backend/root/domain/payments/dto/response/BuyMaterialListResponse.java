package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.coupon.dto.response.CouponListResponse;

/**
 * 결제자료 화면 Response
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuyMaterialListResponse {
    private CouponListResponse couponListResponse;
    private int totalPrice;

    public static BuyMaterialListResponse of(CouponListResponse couponListResponse,
                                             int totalPrice){
        return BuyMaterialListResponse.builder()
                .couponListResponse(couponListResponse)
                .totalPrice(totalPrice)
                .build();
    }
}
