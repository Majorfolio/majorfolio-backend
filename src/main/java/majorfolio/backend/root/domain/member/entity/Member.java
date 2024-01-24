package majorfolio.backend.root.domain.member.entity;

/**
 * Memeber
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * memeber테이블 생성
 *
 * @author 김영록
 * @version 0.0.1
 *
 * member 테이블 수정(01.24)
 *
 * @author 김영록
 * @version 0.0.1
 *
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String nickname;
    private Boolean emailVerified;
    @ColumnDefault("true")
    private Boolean personalInformationIsagree;
    @ColumnDefault("true")
    private Boolean serviceIsagree;
    private String status;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="kakao_id")
    private UserToken userToken;

    public static Member of(String email, String nickname, Boolean emailVerified, String status, UserToken userToken){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .emailVerified(emailVerified)
                .status(status)
                .userToken(userToken)
                .build();
    }
}
