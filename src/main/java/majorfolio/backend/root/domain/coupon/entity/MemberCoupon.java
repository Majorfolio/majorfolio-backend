package majorfolio.backend.root.domain.coupon.entity;

import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;

@Entity
@Table(name = "UserCoupon")
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;
}
