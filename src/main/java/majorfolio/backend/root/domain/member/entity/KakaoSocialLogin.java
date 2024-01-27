/**
 * UserToken
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * user-token 테이블 생성
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KakaoSocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="kakao_id")
    private Long id;
    private Long kakaoNumber;
    private String nonce;
    private String state;
    private String refreshToken;


    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
}
