package majorfolio.backend.root.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserCoupon")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    private LocalDateTime usedDate;

    public static MemberCoupon of(Member member, Coupon coupon, LocalDateTime usedDate){
        return MemberCoupon.builder()
                .member(member)
                .coupon(coupon)
                .usedDate(usedDate)
                .build();
    }
}
