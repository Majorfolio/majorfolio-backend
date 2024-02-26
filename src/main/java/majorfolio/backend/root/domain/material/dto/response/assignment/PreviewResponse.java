package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewResponse {
    private List<String> previews;

    public static PreviewResponse of(
            List<String> previews
    ){
        return PreviewResponse.builder().
                previews(previews).
                build();
    }
}
