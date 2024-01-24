package majorfolio.backend.root.domain.assignment.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponse {
    private Long id;
    private String nickname;
    private String subjectName;
    private String univ;
    private String major;
    private int like;

    public static AssignmentResponse of(Long id, String nickname, String subjectName,
                                        String univ, String major, int like){
        return AssignmentResponse.builder()
                .id(id)
                .nickname(nickname)
                .subjectName(subjectName)
                .univ(univ)
                .major(major)
                .like(like)
                .build();
    }
}
