package majorfolio.backend.root.domain.material.dto.response.assignment;

/**
 * OtherAssignment
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 과제 상세 페이지(구매자 입장) API에서 응답(MaterialDetailResponse)의 OtherAssignment부분
 *
 * @author 김영록
 * @version 0.0.1
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OtherAssignment {
    private Long id;
    private String title;
    private String nickname;
    private String university;
    private String major;
    private String semester;
    private String professor;
    private int like;

    public static OtherAssignment of(Long id, String title, String nickname, String university, String major,
                                     String semester, String professor, int like){
        return OtherAssignment.builder()
                .id(id)
                .title(title)
                .nickname(nickname)
                .university(university)
                .major(major)
                .semester(semester)
                .professor(professor)
                .like(like)
                .build();
    }
}
