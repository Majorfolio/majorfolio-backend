package majorfolio.backend.root.domain.member.entity;

<<<<<<< HEAD
/**
 * Memeber
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
=======
>>>>>>> ba88a3e (feat: domain -> entity로 패키지 구조 변경 && assignment, analytics entity 생성)
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
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
 */

/**
 * member builder 생성 및 수정 (01.24)
 *
 * @author 김태혁
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

    public static Member of(String email, String nickname, Boolean emailVerified,
                            Boolean personalInformationIsagree, Boolean serviceIsagree,
                            String status, UserToken userToken){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .emailVerified(emailVerified)
                .personalInformationIsagree(personalInformationIsagree)
                .serviceIsagree(serviceIsagree)
                .status(status)
                .userToken(userToken)
                .build();
    }
=======

@Entity
@Table(name = "memeber")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private int profilePic;

    @Column(nullable = false)
    private boolean emailVerified;

    public static Member of(Long id, String kakaoId, String email, String nickname, int profilePic, Boolean emailVerified) {
        return Member.builder()
                .id(id)
                .kakaoId(kakaoId)
                .email(email)
                .nickname(nickname)
                .profilePic(profilePic)
                .emailVerified(emailVerified)
                .build();
    }

>>>>>>> ba88a3e (feat: domain -> entity로 패키지 구조 변경 && assignment, analytics entity 생성)
}
