package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDetailResponse {
    private Long id;
    private String imageUrl;
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
    private int pages;
    private List<OtherAssignment> otherAssignmentList;
}
