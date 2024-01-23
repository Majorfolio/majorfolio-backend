package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserToken(UserToken userToken);
}
