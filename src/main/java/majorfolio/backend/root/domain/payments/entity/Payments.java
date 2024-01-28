package majorfolio.backend.root.domain.payments.entity;


import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;
import java.time.LocalDateTime;


@Entity
@Table(name = "Payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private Double amount;

    private LocalDateTime timestamp;
}
