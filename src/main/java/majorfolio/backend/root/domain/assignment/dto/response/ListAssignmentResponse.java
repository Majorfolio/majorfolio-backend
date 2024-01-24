package majorfolio.backend.root.domain.assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ListAssignmentResponse {
    private List<AssignmentResponse> newUpload;
    private List<AssignmentResponse> best;
    private List<AssignmentResponse> latest;

    public static ListAssignmentResponse of(List<AssignmentResponse> newUpload,
                                            List<AssignmentResponse> best,
                                            List<AssignmentResponse> latest){
        return ListAssignmentResponse.builder()
                .newUpload(newUpload)
                .best(best)
                .latest(latest)
                .build();
    }
}
