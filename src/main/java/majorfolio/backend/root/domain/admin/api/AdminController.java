package majorfolio.backend.root.domain.admin.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.service.AdminService;
import majorfolio.backend.root.domain.report.entity.MaterialReport;
import majorfolio.backend.root.domain.report.entity.MemberReport;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/report/member")
    public BaseResponse<List<MemberReport>> getMemberReport(){
        return new BaseResponse<>(adminService.getMemberReport());
    }

    @GetMapping("/report/material")
    public BaseResponse<List<MaterialReport>> getMaterialReport(){
        return new BaseResponse<>(adminService.getMaterialReport());
    }

    @PatchMapping("/report/member/{reportId}")
    public BaseResponse<String> changeMemberReport(@PathVariable(name = "reportId") Long reportId){
        return new BaseResponse<>(adminService.changeMemberReport(reportId));
    }

    @PatchMapping("/report/material/{reportId}")
    public BaseResponse<String> changeMaterialReport(@PathVariable(name = "reportId") Long reportId){
        return new BaseResponse<>(adminService.changeMaterialReport(reportId));
    }
}
