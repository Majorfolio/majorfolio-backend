package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssignmentDownloadResponse {
    private String url;

    public static AssignmentDownloadResponse of(String url){
        return AssignmentDownloadResponse.builder()
                .url(url)
                .build();
    }
}
