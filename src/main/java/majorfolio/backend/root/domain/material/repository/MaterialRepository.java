package majorfolio.backend.root.domain.material.repository;

import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.member.entity.Member;
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
    /**
     * 페이지에 따라 모든 과제의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByOrderByCreatedAtDescIdAsc(Pageable pageable);

    /**
     * 페이지에 따라 모든 과제의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByOrderByTotalRecommendDescIdAsc(Pageable pageable);

    /**
     * 페이지에 따라 내 학교의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByMemberUniversityNameOrderByCreatedAtDescIdAsc(String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학교의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByMemberUniversityNameOrderByTotalRecommendDescIdAsc(String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학과의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByMajorOrderByCreatedAtDescIdAsc(String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학과의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByMajorOrderByTotalRecommendDescIdAsc(String universityName, Pageable pageable);

    /**
     * 페이지에 따라 이 수업의 모든 자료
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByClassNameAndProfessorAndMember_UniversityNameAndMajor(String className, String professor, String universityName, String major, Pageable pageable);

    /**
     * 페이지에 따라 판매자의 모든 자료
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByMember(Member member, Pageable pageable);

}
