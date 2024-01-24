package majorfolio.backend.root.domain.cart.entity;

import jakarta.persistence.*;
import majorfolio.backend.root.domain.assignment.entity.Assignment;

import java.time.LocalDateTime;

@Entity
@Table(name = "CartItem")
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

    private Integer quantity;

    private LocalDateTime timestamp;
}
