/**
 * EmailDB
 *
 * 0.0.1
 *
 * 2024.01.28
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * EmailDB DB테이블 처리
 *
 * @author 김영록
 * @version 0.0.1
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    private String email;
    private String code;
    private LocalDateTime expire;
    private Boolean status;
    private LocalDateTime emailDate;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "kakao_id")
    private KakaoSocialLogin kakaoSocialLogin;

}
