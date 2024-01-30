/**
 * FollowerListRepository
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.FollwerList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FollwerList의 Reposiotory정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface FollwerListRepository extends JpaRepository<FollwerList, Long> {
}
