package majorfolio.backend.root.domain.material.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.assignment.MaterialDetailResponse;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.entity.Preview;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.FollowerList;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.FollowerRepository;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.SellListItemRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {
    private final MaterialRepository materialRepository;
    private final MemberRepository memberRepository;
    private final SellListItemRepository sellListItemRepository;
    private final FollowerRepository followerRepository;
    public MaterialDetailResponse showDetailMaterial(Long materialId){
        //id, 닉네임, 좋아요수, 북마크수, 제목, 설명, 대학교, 학과, 학기, 과목, 교수명, 학점, 점수, 만점, 페이지수
        Material material = materialRepository.findById(materialId).get();
        Member member = material.getMember();

        String nicnkname = member.getNickName();
        int like = material.getTotalRecommend();
        int bookmark = material.getTotalBookmark();
        String description = material.getDescription();
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
        Long sellCount = sellListItemRepository.countByMaterial(material);

        FollowerList followerList = member.getFollowerList();
        Long followerCount = followerRepository.countByFollwerList(followerList);

        // 이 수업의 다른 과제(판매순 5개) 추출

    }
}
