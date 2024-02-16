package majorfolio.backend.root.domain.payments.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매하는 자료의 이름을 담고있는 Response
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MaterialNameResponse {
    private String assignmentName;

    public static MaterialNameResponse of(String assignmentName){
        return MaterialNameResponse
                .builder()
                .assignmentName(assignmentName)
                .build();
    }
}
