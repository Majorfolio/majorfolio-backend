/**
 * MaterialDetailResponse
 *
 * 0.0.1
 *
 * 2024.02.04
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 과제 상세 페이지(구매자입장) API에서 응답에 해당하는 객체
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDetailResponse {
    private Long id;
    private String imageUrl;
    private LocalDateTime updateTime;
    private String nickName;
    private int like;
    private int bookmark;
    private String title;
    private String description;
    private Long sell;
    private Long follower;
    private String university;
    private String major;
    private String semester;
    private String subjectTitle;
    private String professor;
    private String grade;
    private int score;
    private int fullScore;
    private int pages;
    private List<OtherAssignment> otherAssignmentList;

    public static MaterialDetailResponse of(
            Long id,
            String imageUrl,
            LocalDateTime updateTime,
            String nickName,
            int like,
            int bookmark,
            String title,
            String description,
            Long sell,
            Long follower,
            String university,
            String major,
            String semester,
            String subjectTitle,
            String professor,
            String grade,
            int score,
            int fullScore,
            int pages,
            List<OtherAssignment> otherAssignmentList
    ){
        return MaterialDetailResponse.builder()
                .id(id)
                .imageUrl(imageUrl)
                .updateTime(updateTime)
                .nickName(nickName)
                .like(like)
                .bookmark(bookmark)
                .title(title)
                .description(description)
                .sell(sell)
                .follower(follower)
                .university(university)
                .major(major)
                .semester(semester)
                .subjectTitle(subjectTitle)
                .professor(professor)
                .grade(grade)
                .score(score)
                .fullScore(fullScore)
                .pages(pages)
                .otherAssignmentList(otherAssignmentList)
                .build();
    }
}
