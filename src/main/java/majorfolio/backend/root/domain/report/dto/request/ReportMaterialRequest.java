package majorfolio.backend.root.domain.report.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportMaterialRequest {
    private Long reportedMaterialId;
    private String reason;
    private String description;
}
