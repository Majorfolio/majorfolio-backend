package majorfolio.backend.root.domain.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Table(name = "Carts")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    private LocalDateTime timestamp;

    public static Cart of(Member member, LocalDateTime timestamp){
        return Cart.builder()
                .member(member)
                .timestamp(timestamp)
                .build();
    }

}
