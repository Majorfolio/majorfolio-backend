/**
 * BuyListRepository
 *
 * 0.0.1
 *
 * 2024.01.30
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.member.entity.BuyList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BuyList의 Repository정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface BuyListRepository extends JpaRepository<BuyList, Long> {
}
