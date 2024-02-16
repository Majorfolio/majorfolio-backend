package majorfolio.backend.root.domain.payments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매자료 couponId 리퀘스트
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponIdRequest {
    private Long couponId;
}
