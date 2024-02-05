package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialMyDetailResponse {
    private Long id;
    private String image;
    private LocalDateTime updateTime;
    private String nickName;
    private int like;
    private int bookmark;
    private String title;
    private String description;
    private String university;
    private String major;
    private String semester;
    private String subjectTitle;
    private String professor;
    private String grade;
    private int score;
    private int pages;
    private String status;

    public static MaterialMyDetailResponse of(
            Long id,
            String image,
            LocalDateTime updateTime,
            String nickName,
            int like,
            int bookmark,
            String title,
            String description,
            String university,
            String major,
            String semester,
            String subjectTitle,
            String professor,
            String grade,
            int score,
            int pages,
            String status
    ){
        return MaterialMyDetailResponse.builder()
                .id(id)
                .image(image)
                .updateTime(updateTime)
                .nickName(nickName)
                .like(like)
                .bookmark(bookmark)
                .title(title)
                .description(description)
                .university(university)
                .major(major)
                .semester(semester)
                .subjectTitle(subjectTitle)
                .professor(professor)
                .grade(grade)
                .score(score)
                .pages(pages)
                .status(status)
                .build();
    }
}
