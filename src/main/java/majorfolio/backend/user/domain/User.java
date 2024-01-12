package majorfolio.backend.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
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

    public static User of(Long id, String kakaoId, String email, String nickname, int profilePic, Boolean emailVerified) {
        return User.builder()
                .id(id)
                .kakaoId(kakaoId)
                .email(email)
                .nickname(nickname)
                .profilePic(profilePic)
                .emailVerified(emailVerified)
                .build();
    }

}
