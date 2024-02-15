/**
 * PreviewRepository
 *
 * 2024.02.16
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Preview;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Preview DB의 Repository
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface PreviewRepository extends JpaRepository<Preview, Long> {
}
