/**
 * EmailRepository
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.EmailDB;
import majorfolio.backend.root.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EmailDB 테이블의 Repository
 * @author 김영록
 * @version 0.0.1
 */
public interface EmailDBRepository extends JpaRepository<EmailDB, Long> {
    Boolean existsEmailDBByEmailAndStatus(String email, Boolean status);
    EmailDB findByEmail(String email);

    EmailDB findByMember(Member member);
}
