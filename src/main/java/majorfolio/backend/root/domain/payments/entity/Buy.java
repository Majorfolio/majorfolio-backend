package majorfolio.backend.root.domain.payments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.assignment.entity.Assignment;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Buy")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "payments_id", nullable = false)
    private Payments payments;

    private String buyOrderStatus;

    private LocalDateTime saleDate;

    public static Buy of(Member member, Assignment assignment, Payments payments,
                         LocalDateTime saleDate){
        return Buy.builder()
                .member(member)
                .assignment(assignment)
                .payments(payments)
                .buyOrderStatus("구매 대기")
                .saleDate(saleDate)
                .build();
    }
}
