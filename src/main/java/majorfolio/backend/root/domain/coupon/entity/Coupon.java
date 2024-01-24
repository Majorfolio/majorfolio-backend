package majorfolio.backend.root.domain.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "Coupons")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Double discountAmount;

    private LocalDateTime expiryDate;
    private String status;

    public static Coupon of(String code, Double discountAmount, LocalDateTime expiryDate, String status){
        return Coupon.builder()
                .code(code)
                .discountAmount(discountAmount)
                .expiryDate(expiryDate)
                .status(status)
                .build();
    }
}
