/**
 * Memeber
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * memeber테이블 생성
 *
 * @author 김영록
 * @version 0.0.1
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

    private String nickName;
    private String email;
    private Boolean emailVerified;
    private Boolean personalInformationIsagree;
    private Boolean serviceIsagree;
    private String status;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="kakao_id")
    private UserToken userToken;

    /**
     * 빌더 패턴으로 객체 생성 메소드
     * @param email
     * @param nickName
     * @param emailVerified
     * @param personalInformationIsagree
     * @param serviceIsagree
     * @param status
     * @param userToken
     * @return
     */
    public static Member of(String email,String nickName, Boolean emailVerified,
                            Boolean personalInformationIsagree, Boolean serviceIsagree,
                            String status, UserToken userToken){
        return Member.builder()
                .nickName(nickName)
                .email(email)
                .emailVerified(emailVerified)
                .personalInformationIsagree(personalInformationIsagree)
                .serviceIsagree(serviceIsagree)
                .status(status)
                .userToken(userToken)
                .build();
    }
}
