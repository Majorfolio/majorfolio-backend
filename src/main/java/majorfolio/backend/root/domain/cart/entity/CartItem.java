package majorfolio.backend.root.domain.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import majorfolio.backend.root.domain.assignment.entity.Assignment;

import java.time.LocalDateTime;

@Entity
@Table(name = "CartItem")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    private LocalDateTime dateAdded;

    public static CartItem of(Cart cart, Assignment assignment, LocalDateTime dateAdded){
        return CartItem.builder()
                .cart(cart)
                .assignment(assignment)
                .dateAdded(dateAdded)
                .build();
    }
}
