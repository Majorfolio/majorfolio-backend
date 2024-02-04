/**
 * SellListItemRepository
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.SellListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SellListItem Entity의 Repository정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface SellListItemRepository extends JpaRepository<SellListItem, Long> {

    /**
     * 해당 과제물의 판매수를 알 수 있다.
     * @author 김영록
     * @param materialId
     * @param status
     * @return
     */
    Long countByMaterialIdAndStatus(Long materialId, String status);
}