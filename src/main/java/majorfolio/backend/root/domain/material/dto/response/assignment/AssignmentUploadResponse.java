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
    private Boolean isRegisterPhoneNumber;

    public static AssignmentUploadResponse of(Boolean isRegisterPhoneNumber){
        return AssignmentUploadResponse.builder()
                .isRegisterPhoneNumber(isRegisterPhoneNumber)
                .build();
    }
}
