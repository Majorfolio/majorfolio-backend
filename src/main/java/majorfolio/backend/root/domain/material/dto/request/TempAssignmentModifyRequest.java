package majorfolio.backend.root.domain.material.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TempAssignmentModifyRequest {
    private MultipartFile file;
    private String title;
    private String major;
    private String semester;
    private String className;
    private String professor;
    private String grade;
    private Integer fullScore;
    private Integer score;
    private String description;
    private String isFileModify; // 파일 수정 여부


}
