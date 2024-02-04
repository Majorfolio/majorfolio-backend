/**
 * FollowerRepository
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.Follower;
import majorfolio.backend.root.domain.member.entity.FollowerList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Follower Entity의 연산 작업 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    /**
     * 사용자의 총 팔로우 수를 알 수 있다.
     * @author 김영록
     * @param member
     * @param status
     * @return
     */
    Long countByMemberAndStatus(Long member, Boolean status);
}
