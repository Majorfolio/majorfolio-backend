/**
 * UniversityRepository
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.university.repository;

import majorfolio.backend.root.domain.university.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * University테이블의 Repository
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface UniversityRepository extends JpaRepository<University, Long> {
}
