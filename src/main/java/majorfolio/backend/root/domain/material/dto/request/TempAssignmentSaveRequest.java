package majorfolio.backend.root.domain.material.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TempAssignmentSaveRequest {
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
}
