/**
 * BookmarkRepository
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * Bookmark의 Repository정의
 *
 * @author 김영록
 * @version 0.0.1
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    /**
     * 과제의 총 북마크 수 구하기
     * @author 김영록
     * @param material
     * @param isCheck
     * @return
     */
    Long countByMaterialAndIsCheck(Material material, Boolean isCheck);

    /**
     * 과제의 기간별 북마크 수 구하기
     * @author 김영록
     * @param material
     * @param isCheck
     * @param start
     * @param end
     * @return
     */
    Long countByMaterialAndIsCheckAndDateBetween(Material material, Boolean isCheck,
                                                 LocalDateTime start, LocalDateTime end);
}
