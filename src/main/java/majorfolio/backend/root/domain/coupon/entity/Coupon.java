package majorfolio.backend.root.domain.coupon.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Double discountAmount;

    private LocalDateTime expiryDate;
}
