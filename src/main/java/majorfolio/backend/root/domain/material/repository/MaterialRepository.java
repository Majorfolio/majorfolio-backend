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
    List<Material> findTop5ByStatusOrderByCreatedAtDescIdAsc(String status);
    /**
     * 모든학교에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByStatusOrderByTotalRecommendDescIdAsc(String status);
    /**
     * 내학교에서 자료 생성순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByStatusAndMemberUniversityNameOrderByCreatedAtDescIdAsc(String status, String universityName);
    /**
     * 내학교에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByStatusAndMemberUniversityNameOrderByTotalRecommendDescIdAsc(String status, String universityName);
    /**
     * 내학과에서 자료 생성순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByStatusAndMajorOrderByCreatedAtDescIdAsc(String status, String major);
    /**
     * 내학과에서 자료 좋아요순 5개
     * @author 김태혁
     * @version 0.0.1
     */
    List<Material> findTop5ByStatusAndMajorOrderByTotalRecommendDescIdAsc(String status, String major);
    /**
     * 과제 아이디와 대학 이름에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
    Material findByStatusAndIdAndMemberUniversityName(String status, Long id, String universityName);
    /**
     * 과제 아이디와 학과에 맞는 과제 반환
     * @author 김태혁
     * @version 0.0.1
     */
    Material findByStatusAndIdAndMajor(String status, Long id, String major);
    /**
     * 페이지에 따라 모든 과제의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusOrderByCreatedAtDescIdAsc(String status, Pageable pageable);

    /**
     * 페이지에 따라 모든 과제의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusOrderByTotalRecommendDescIdAsc(String status, Pageable pageable);

    /**
     * 페이지에 따라 내 학교의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndMemberUniversityNameOrderByCreatedAtDescIdAsc(String status, String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학교의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndMemberUniversityNameOrderByTotalRecommendDescIdAsc(String status, String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학과의 최근 업로드 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndMajorOrderByCreatedAtDescIdAsc(String status, String universityName, Pageable pageable);

    /**
     * 페이지에 따라 내 학과의 좋아요 많은 순
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndMajorOrderByTotalRecommendDescIdAsc(String status, String universityName, Pageable pageable);

    /**
     * 페이지에 따라 이 수업의 모든 자료
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndClassNameAndProfessorAndMember_UniversityNameAndMajor(String status, String className, String professor, String universityName, String major, Pageable pageable);

    /**
     * 페이지에 따라 판매자의 모든 자료
     * @author 김태혁
     * @version 0.0.1
     */
    Page<Material> findByStatusAndMember(String status, Member member, Pageable pageable);

    /**
     * 이 수업의 다른 자료리스트 반환
     * @author 김영록
     * @param className
     * @return
     */
    List<Material> findAllByClassNameAndStatus(String className, String status);

    /**
     * 해당 유저의 과제가 판매중인 리스트를 반환
     * @param member
     * @param status
     * @return
     */
    List<Material> findAllByMemberAndStatus(Member member, String status);

    /**
     * page에 따라 멤버의 모든 과제를 반환 ( 업데이트 순)
     * @param member
     * @param pageable
     * @return
     */
    Page<Material> findAllByMemberOrderByUpdatedAtDesc(Member member, Pageable pageable);

    Material findByMember(Member member);

}
