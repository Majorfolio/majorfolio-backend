package majorfolio.backend.root.domain.payments.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefundInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_info_id")
    private Long id;
    private Long memberId;
    private Long buyInfoId;
    private String account;
    private int price;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String status;
    private boolean processing;

    public static RefundInfo of(BuyInfo buyInfo, String account){
        return RefundInfo.builder()
                .memberId(buyInfo.getBuyerId())
                .buyInfoId(buyInfo.getId())
                .account(account)
                .price(buyInfo.getPrice())
                .status("accepted")
                .processing(false)
                .build();
    }
}
