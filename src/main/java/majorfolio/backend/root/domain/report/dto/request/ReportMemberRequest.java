package majorfolio.backend.root.domain.report.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportMemberRequest {
    private Long reportedMemberId;
    private String reason;
    private String description;
}
