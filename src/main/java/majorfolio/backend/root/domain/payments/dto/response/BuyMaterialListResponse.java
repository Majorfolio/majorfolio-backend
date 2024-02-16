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
    private int totalCoupon;
    private int canUseCoupon;

    public static BuyMaterialListResponse of(int totalCoupon,
                                             int canUseCoupon){
        return BuyMaterialListResponse.builder()
                .totalCoupon(totalCoupon)
                .canUseCoupon(canUseCoupon)
                .build();
    }
}
