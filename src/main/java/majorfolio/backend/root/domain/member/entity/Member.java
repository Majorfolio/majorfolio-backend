
/**
 * Member
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */

package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Member테이블 생성
 *
 * @author 김영록
 * @version 0.0.1
 *
 * member 테이블 수정(01.24)
 *
 * @author 김영록
 * @version 0.0.1
 *
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickName;
    private String status;
    private String universityName;
    private String major1;
    private String major2;
    private int studentId;
    private String profileImage;

    @Column(columnDefinition = "boolean default false")
    private Boolean personalAgree;
    @Column(columnDefinition = "boolean default false")
    private Boolean serviceAgree;
    @Column(columnDefinition = "boolean default false")
    private Boolean marketingAgree;
    @Column(columnDefinition = "boolean default false")
    private Boolean noticeEvent;
    @Column(columnDefinition = "boolean default false")
    private Boolean advertisement;
    @Column(columnDefinition = "boolean default false")
    private Boolean postComment;
    @Column(columnDefinition = "boolean default false")
    private Boolean emailAlarm;


    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToOne
    @JoinColumn(name = "buyList_id")
    private BuyList buyList;

    @OneToOne
    @JoinColumn(name = "sellList_id")
    private SellList sellList;

    @OneToOne
    @JoinColumn(name = "follower_id")
    private FollwerList follwerList;

    @OneToOne
    @JoinColumn(name = "couponBox_id")
    private CouponBox couponBox;

}
