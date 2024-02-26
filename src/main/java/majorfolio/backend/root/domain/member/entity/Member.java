
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
import lombok.*;
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
@Setter
@DynamicInsert
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickName;
    @Column(columnDefinition = "varchar(255) default 'active'")
    private String status;
    private String universityName;
    private String major1;
    private String major2;
    private int studentId;
    private String profileImage;
    private String phoneNumber;

    @Column(columnDefinition = "Boolean default false")
    private Boolean personalAgree;
    @Column(columnDefinition = "Boolean default false")
    private Boolean serviceAgree;
    @Column(columnDefinition = "Boolean default false")
    private Boolean marketingAgree;
    @Column(columnDefinition = "Boolean default false")
    private Boolean noticeEvent;
    @Column(columnDefinition = "Boolean default false")
    private Boolean advertisement;
    @Column(columnDefinition = "Boolean default false")
    private Boolean postComment;
    @Column(columnDefinition = "Boolean default false")
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
    private FollowerList followerList;

    @OneToOne
    @JoinColumn(name = "couponBox_id")
    private CouponBox couponBox;

    @OneToOne
    @JoinColumn(name = "tempStorage_id")
    private TempStorage tempStorage;

    public static Member of(String nickName, String universityName,
                            String major1, String major2, int studentId,
                            Boolean personalAgree, Boolean serviceAgree,
                            Boolean marketingAgree, Basket basket,
                            BuyList buyList, SellList sellList,
                            FollowerList followerList, CouponBox couponBox,
                            TempStorage tempStorage){
        return Member.builder()
                .nickName(nickName)
                .universityName(universityName)
                .major1(major1)
                .major2(major2)
                .studentId(studentId)
                .personalAgree(personalAgree)
                .serviceAgree(serviceAgree)
                .marketingAgree(marketingAgree)
                .basket(basket)
                .buyList(buyList)
                .sellList(sellList)
                .followerList(followerList)
                .couponBox(couponBox)
                .tempStorage(tempStorage)
                .build();
    }

}
