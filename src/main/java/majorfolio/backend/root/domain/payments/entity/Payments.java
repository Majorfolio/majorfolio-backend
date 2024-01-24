package majorfolio.backend.root.domain.payments.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;
import java.time.LocalDateTime;


@Entity
@Table(name = "Payments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private Double totalAmount;

    private LocalDateTime paymentDate;

    public static Payments of(Member member, Double totalAmount, LocalDateTime paymentDate){
        return Payments.builder()
                .member(member)
                .totalAmount(totalAmount)
                .paymentDate(paymentDate)
                .build();
    }
}
