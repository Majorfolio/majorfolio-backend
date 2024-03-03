package majorfolio.backend.root.domain.material.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TempAssignmentDetailResponse {
    private String link;
    private String title;
    private String major;
    private String semester;
    private String className;
    private String professor;
    private String grade;
    private Integer fullScore;
    private Integer score;
    private String description;

    public static TempAssignmentDetailResponse of(
            String link,
            String title,
            String major,
            String semester,
            String className,
            String professor,
            String grade,
            Integer fullScore,
            Integer score,
            String description
    ){
        return TempAssignmentDetailResponse.builder()
                .link(link)
                .title(title)
                .major(major)
                .semester(semester)
                .className(className)
                .professor(professor)
                .grade(grade)
                .fullScore(fullScore)
                .score(score)
                .description(description)
                .build();
    }
}
