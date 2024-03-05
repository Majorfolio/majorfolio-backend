package majorfolio.backend.root.domain.material.dto.response.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssignmentUploadResponse {
    private Long materialId;

    public static AssignmentUploadResponse of(Long materialId){
        return AssignmentUploadResponse.builder()
                .materialId(materialId)
                .build();
    }
}
