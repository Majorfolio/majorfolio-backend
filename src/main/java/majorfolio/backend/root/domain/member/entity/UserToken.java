package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
}
