package majorfolio.backend.root.domain.payments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.coupon.dto.response.CouponListResponse;

import java.util.List;

/**
 * BuyInfo request 정의
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateBuyInfoRequest {
    private List<MaterialIdRequest> assignmentIdList;
    private List<CouponIdRequest> couponIdList;
    private int totalPrice;
}
