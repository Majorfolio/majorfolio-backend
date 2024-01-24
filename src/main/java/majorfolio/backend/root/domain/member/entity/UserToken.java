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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * user-token 테이블 생성
 * @author 김영록
 * @version 0.0.1
 */

/**
 * user-token builder 생성 (01.24)
 * @author 김태혁
 * @version 0.0.1
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserToken {
    @Id
    @Column(name="kakao_id")
    private Long id;
    private String nonce;
    private String refreshToken;
    private String state;

    public static UserToken of(Long id, String nonce, String refreshToken, String state){
        return UserToken.builder()
                .id(id)
                .nonce(nonce)
                .refreshToken(refreshToken)
                .state(state)
                .build();
    }
}
