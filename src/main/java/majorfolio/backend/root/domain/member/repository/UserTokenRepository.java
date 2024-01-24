package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByRefreshToken(String refreshToken);
}
