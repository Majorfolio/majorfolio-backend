package majorfolio.backend.root.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostNoticeRequest {
    private MultipartFile multipartFile;
    @NotBlank(message = "title : 제목이 비어있으면 안됩니다.")
    private String title;
}
