/**
 * MemberRepository
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * member테이블의 Repository
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserToken(UserToken userToken);
    Member findMemberById(Long userId);
}
