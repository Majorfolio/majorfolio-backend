package majorfolio.backend.root.domain.report.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.report.dto.request.ReportMaterialRequest;
import majorfolio.backend.root.domain.report.dto.request.ReportMemberRequest;
import majorfolio.backend.root.domain.report.service.ReportService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/member")
    public BaseResponse<String> reportMember(@RequestBody ReportMemberRequest reportMemberRequest,
                                                           HttpServletRequest request){
        return new BaseResponse<>(reportService.reportMember(reportMemberRequest, request));
    }

    @PostMapping("/material")
    public BaseResponse<String> reportMaterial(@RequestBody ReportMaterialRequest reportMaterialRequest,
                                             HttpServletRequest request){
        return new BaseResponse<>(reportService.reportMaterial(reportMaterialRequest, request));
    }
}
