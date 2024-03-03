package majorfolio.backend.root.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.ValidFile;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostEventRequest {
    @ValidFile(message = "file : 파일이 비어있으면 안됩니다.")
    private MultipartFile file;
    @NotBlank(message = "title : 제목이 비어있으면 안됩니다.")
    private String title;
}
