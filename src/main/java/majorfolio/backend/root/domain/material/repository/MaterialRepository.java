package majorfolio.backend.root.domain.material.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * material의 DB에 데이터를 다루기 위한 레포지토리 생성
 * @author 김태혁
 * @version 0.0.1
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {
    /**
     * 모든학교에서 자료 생성순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByOrderByCreatedAtDescIdAsc();
    /**
     * 모든학교에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByOrderByTotalRecommendDescIdAsc();
    /**
     * 내학교에서 자료 생성순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByMemberUniversityNameOrderByCreatedAtDescIdAsc(String universityName);
    /**
     * 내학교에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByMemberUniversityNameOrderByTotalRecommendDescIdAsc(String universityName);
    /**
     * 내학과에서 자료 생성순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByMajorOrderByCreatedAtDescIdAsc(String major);
    /**
     * 내학과에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByMajorOrderByTotalRecommendDescIdAsc(String major);
    /**
     * 과제 아이디와 대학 이름에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
    Material findByIdAndMemberUniversityName(Long id, String universityName);
    /**
     * 과제 아이디와 학과에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
    Material findByIdAndMajor(Long id, String major);
    Page<Material> findByOrderByCreatedAtDescIdAsc(Pageable pageable);
    Page<Material> findByOrderByTotalRecommendDescIdAsc(Pageable pageable);

    Page<Material> findByMemberUniversityNameOrderByCreatedAtDescIdAsc(String universityName, Pageable pageable);

    Page<Material> findByMemberUniversityNameOrderByTotalRecommendDescIdAsc(String universityName, Pageable pageable);

    Page<Material> findByMajorOrderByCreatedAtDescIdAsc(String universityName, Pageable pageable);

    Page<Material> findByMajorOrderByTotalRecommendDescIdAsc(String universityName, Pageable pageable);
}
