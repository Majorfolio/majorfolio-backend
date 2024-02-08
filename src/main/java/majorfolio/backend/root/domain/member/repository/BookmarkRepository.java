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
import majorfolio.backend.root.domain.member.entity.Likes;
import majorfolio.backend.root.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Bookmark findByMemberAndMaterial(Member member, Material material);

    /**
     * 내가 북마크한 리스트 모아보기 jpql(최근 북마크한 순으로)
     * @author 김영록
     * @param member
     * @param isCheck
     * @param pageable
     * @return
     */
    Page<Bookmark> findByMemberAndIsCheckOrderByDateDescIdAsc(Member member, boolean isCheck, Pageable pageable);
}
