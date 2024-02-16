package majorfolio.backend.root.domain.payments.entity;

import jakarta.persistence.*;
import lombok.*;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 결제를 통해 생성되는 구매정보에 대한 클래스
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyInfo_id")
    private Long id;

    private String code;
    private int price;
    private Long buyerId;
    private Boolean isPay;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String status;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "buyInfo")
    private List<BuyListItem> buyListItems = new ArrayList<>();

    public void addBuyListItem(BuyListItem buyListItem)
    {
        buyListItems.add(buyListItem);
        buyListItem.setBuyInfo(this);
    }

    public static BuyInfo of(String code, int price, Long buyerId, String status){
        return BuyInfo.builder()
                .code(code)
                .price(price)
                .buyerId(buyerId)
                .isPay(false)
                .status(status)
                .build();
    }
}
