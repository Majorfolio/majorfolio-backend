/**
 * ViewRepository
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * View의 Repository정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface ViewRepository extends JpaRepository<View, Long> {

    /**
     * 과제별 총 조회수 구하기
     * @author 김영록
     * @param material
     * @return
     */
    Long countByMaterial(Material material);

    /**
     * 과제별 기간별 조회수 구하기
     * @author 김영록
     * @param material
     * @param start
     * @param end
     * @return
     */
    Long countByMaterialAndDateBetween(Material material, LocalDateTime start, LocalDateTime end);
}
