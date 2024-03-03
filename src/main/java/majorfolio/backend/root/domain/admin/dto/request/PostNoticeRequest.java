package majorfolio.backend.root.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostNoticeRequest {
    private MultipartFile file;
    @NotBlank(message = "title : 제목이 비어있으면 안됩니다.")
    private String title;
}
