/**
 * MemberUnivRepository
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.university.repository;

import majorfolio.backend.root.domain.university.entity.MemberUniv;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MemberUniv테이블의 Repository
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface MemberUnivRepository extends JpaRepository<MemberUniv, Long> {
}
