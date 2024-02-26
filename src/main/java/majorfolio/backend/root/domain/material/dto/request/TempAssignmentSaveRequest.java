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
    @Pattern(regexp = "\\d{2}-\\d", message = "semester : 학기 정보 형식은 00-0형식이어야 합니다.")
    private String semester;
    private String subjectName;
    private String professor;
    @Pattern(regexp = "A[+-]?|B[+-]?|C[+-]?|D[+-]?|F", message = "grade : 올바른 학점 형식이어야 합니다.")
    private String grade;
    private Integer fullScore;
    private Integer score;
    @Size(max = 80, message = "description : 설명은 80자로 제한됩니다.")
    private String description;
}
