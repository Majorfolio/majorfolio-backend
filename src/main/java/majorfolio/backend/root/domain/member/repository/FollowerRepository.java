package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.Follower;
import majorfolio.backend.root.domain.member.entity.FollowerList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Long countByFollwerList(FollowerList followerList);
}
