package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OtherAssignment {
    private Long id;
    private String nickname;
    private String university;
    private String major;
    private String semester;
    private String professor;
    private int like;
}
