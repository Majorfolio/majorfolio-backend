/**
 * AssignmentService
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialMyDetailResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.BookmarkStat;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.MaterialStatsResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.OtherAssignment;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.SaleStat;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.ViewStat;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.entity.Preview;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.*;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.NotMatchMaterialAndMemberException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.INVALID_TOKEN;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_MATCH_MATERIAL_AND_MEMBER;

/**
 * assignment/** 요청의 서비스 구현
 *
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {
    private final MaterialRepository materialRepository;
    private final SellListItemRepository sellListItemRepository;
    private final FollowerRepository followerRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ViewRepository viewRepository;

    /**
     * 과제 상세페이지(구매자 입장)의 서비스 메소드
     * @author 김영록
     * @param materialId
     * @return
     */
    public MaterialDetailResponse showDetailMaterial(Long materialId){
        //id, 닉네임, 좋아요수, 북마크수, 제목, 설명, 대학교, 학과, 학기, 과목, 교수명, 학점, 점수, 만점, 페이지수
        Material material = materialRepository.findById(materialId).get();
        Member member = material.getMember();

        String nickname = member.getNickName();
        LocalDateTime updateTime = material.getUpdatedAt();
        int like = material.getTotalRecommend();
        int bookmark = material.getTotalBookmark();
        String title = material.getName();
        String description = material.getDescription();
        String univ = member.getUniversityName();
        String major = material.getMajor();
        String semester = material.getSemester();
        String className = material.getClassName();
        String professor = material.getProfessor();
        String grade = material.getGrade();
        int score = material.getScore();
        int fullscore = material.getFullScore();
        int pages = material.getPage();


        //미리보기 이미지 url 추출
        Preview preview = material.getPreview();
        String image = preview.getImage1();

        // 판매수, 팔로워 수 추출
        Long sellCount = sellListItemRepository.countByMaterialIdAndStatus(material.getId(), "complete");

        //FollowerList followerList = member.getFollowerList();
        Long memberId = member.getId();
        Long followerCount = followerRepository.countByMemberAndStatus(memberId, true);

        // 이 수업의 다른 과제(판매순 5개) 추출
        List<Material> materials = materialRepository.findAllByClassNameAndStatus(className, "active");
        log.info(materials.toString());
        Map<Long, Long> materialSellInfo = new HashMap<>();
        for (Material value : materials) {
            // 대학명도 같아야 함
            if(value.getMember().getUniversityName().equals(univ) && !value.getId().equals(materialId)) {
                Long id = value.getId();
                Long sell = sellListItemRepository.countByMaterialIdAndStatus(id, "complete");
                materialSellInfo.put(id, sell);
            }
        }
        log.info(materialSellInfo.toString());

        //판매순 정렬
        List<Long> keySet = new ArrayList<>(materialSellInfo.keySet());
        keySet.sort((o1, o2) -> materialSellInfo.get(o2).compareTo(materialSellInfo.get(o1)));

        //판매순 5개 뽑아내기
        List<OtherAssignment> materialsTop5 = new ArrayList<>();
        for(int i=0; i<5; i++){
            Long id;
            try{
                id = keySet.get(i);
            }catch (IndexOutOfBoundsException e){
                break;
            }
            if(materialRepository.existsById(id)){
                Material m = materialRepository.findById(id).get();
                OtherAssignment o = OtherAssignment.of(id, m.getName(), m.getMember().getNickName(),
                        m.getMember().getUniversityName(), m.getMajor(), m.getSemester(),
                        m.getProfessor(), m.getTotalRecommend());
                materialsTop5.add(i, o);
            }

        }

        return MaterialDetailResponse.of(
                materialId,
                image,
                updateTime,
                nickname,
                like,
                bookmark,
                title,
                description,
                sellCount,
                followerCount,
                univ,
                major,
                semester,
                className,
                professor,
                grade,
                score,
                fullscore,
                pages,
                materialsTop5
        );
    }

    /**
     * 과제 상세페이지(판매자 입장) API 서비스 구현
     * @param kakaoId
     * @param materialId
     * @return
     */
    public MaterialMyDetailResponse showMyDetailMaterial(Long kakaoId, Long materialId){

        //판매자 입장 페이지에 들어갈 수 있는지 자격 증명
        validateMaterialAndMember(kakaoId, materialId);

        Material material = materialRepository.findById(materialId).get();
        Member member = material.getMember();

        //미리보기 이미지 url 추출
        Preview preview = material.getPreview();
        String image = preview.getImage1();

        LocalDateTime updateTime = material.getUpdatedAt();
        String nickname = member.getNickName();
        int like = material.getTotalRecommend();
        int bookmark = material.getTotalBookmark();
        String title = material.getName();
        String description = material.getDescription();
        String university = member.getUniversityName();
        String major = material.getMajor();
        String semester = material.getSemester();
        String subjectTitle = material.getClassName();
        String professor = material.getProfessor();
        String grade = material.getGrade();
        int score = material.getScore();
        int pages = material.getPage();
        String status = material.getStatus();

        return MaterialMyDetailResponse.of(
                materialId,
                image,
                updateTime,
                nickname,
                like,
                bookmark,
                title,
                description,
                university,
                major,
                semester,
                subjectTitle,
                professor,
                grade,
                score,
                pages,
                status
        );
    }

    /**
     * 과제 통계(판매자 입장) API 서비스 구현
     * @param kakaoId
     * @param materialId
     * @return
     */
    public MaterialStatsResponse getMaterialStats(Long kakaoId, Long materialId){
        Material material = materialRepository.findById(materialId).get();
        //판매자 입장 페이지에 들어갈 수 있는지 자격 증명
        validateMaterialAndMember(kakaoId, materialId);

        // 날짜 연산
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime todayStart = today
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        LocalDateTime todayEnd = today.plusDays(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);


        // 이번 주 날짜 간격 구하기
        int day = today.get(DAY_OF_WEEK);
        if(day==7){
            day=0;
        }

        LocalDateTime startDate = today.minusDays(day)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        log.info(startDate.toString());

        LocalDateTime endDate = startDate.plusDays(6)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        log.info(endDate.toString());

        //판매 통계 구하기
        Long totalSale = sellListItemRepository.countByMaterialIdAndStatus(materialId, "complete");
        Long weeklySale = sellListItemRepository
                .countByMaterialIdAndStatusAndDateBetween(materialId, "complete", startDate, endDate);
        Long todaySale = sellListItemRepository
                .countByMaterialIdAndStatusAndDateBetween(materialId, "complete", todayStart, todayEnd);

        //조회 통계 구하기
        Long totalView = viewRepository.countByMaterial(material);
        Long weeklyView = viewRepository
                .countByMaterialAndDateBetween(material, startDate, endDate);
        Long todayView = viewRepository
                .countByMaterialAndDateBetween(material, todayStart, todayEnd);

        //북마크 통계 구하기
        Long totalBookmark = bookmarkRepository.countByMaterialAndIsCheck(material, true);
        Long weeklyBookmark = bookmarkRepository
                .countByMaterialAndIsCheckAndDateBetween(material, true, startDate, endDate);
        Long todayBookmark = bookmarkRepository
                .countByMaterialAndIsCheckAndDateBetween(material, true, todayStart, todayEnd);

        // 응답 객체 생성
        SaleStat saleStat = SaleStat.of(totalSale, weeklySale, todaySale);
        ViewStat viewStat = ViewStat.of(totalView, weeklyView, todayView);
        BookmarkStat bookmarkStat = BookmarkStat.of(totalBookmark, weeklyBookmark, todayBookmark);

        return MaterialStatsResponse.of(saleStat, viewStat, bookmarkStat);
    }


    /**
     * 판매자 입장에서의 과제 페이지에 들어갈 수 있는지 확인
     * -> 이 메소드 통과하면 자격이 있는 거임
     * @param kakaoId
     * @param materialId
     */
    public void validateMaterialAndMember(Long kakaoId, Long materialId){
        // 요청 사용자의 memberId 추출
        Long memberId = kakaoSocialLoginRepository.findById(kakaoId).get().getMember().getId();
        if(memberId == 0){
            // 인증을 거친 사용자가 아닐 경우
            throw new JwtInvalidException(INVALID_TOKEN);
        }
        //과제 주인 memberId
        Long owner = materialRepository.findById(materialId).get().getMember().getId();

        //과제 주인과 현재 사용자가 다를 때
        if(!Objects.equals(owner, memberId)){
            throw new NotMatchMaterialAndMemberException(NOT_MATCH_MATERIAL_AND_MEMBER);
        }
    }
}
