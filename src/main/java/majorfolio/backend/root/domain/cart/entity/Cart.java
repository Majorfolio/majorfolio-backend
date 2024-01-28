package majorfolio.backend.root.domain.cart.entity;

import jakarta.persistence.*;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private LocalDateTime timestamp;

}
