package majorfolio.backend.root.domain.admin.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.admin.dto.request.PostEventRequest;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostEventResponse;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.service.AdminService;
import majorfolio.backend.root.domain.report.entity.MaterialReport;
import majorfolio.backend.root.domain.report.entity.MemberReport;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/report/member")
    public BaseResponse<List<MemberReport>> getMemberReport() {
        return new BaseResponse<>(adminService.getMemberReport());
    }

    @GetMapping("/report/material")
    public BaseResponse<List<MaterialReport>> getMaterialReport() {
        return new BaseResponse<>(adminService.getMaterialReport());
    }

    @PatchMapping("/report/member/{reportId}")
    public BaseResponse<String> changeMemberReport(@PathVariable(name = "reportId") Long reportId) {
        return new BaseResponse<>(adminService.changeMemberReport(reportId));
    }

    @PatchMapping("/report/material/{reportId}")
    public BaseResponse<String> changeMaterialReport(@PathVariable(name = "reportId") Long reportId) {
        return new BaseResponse<>(adminService.changeMaterialReport(reportId));
    }

    /**
     * 공지사항 작성 API
     *
     * @param postNoticeRequest
     * @return
     * @throws IOException
     */
    @PostMapping("/notice")
    public BaseResponse<PostNoticeResponse> postNotice(@Validated @ModelAttribute PostNoticeRequest postNoticeRequest) throws IOException {
        return new BaseResponse<>(adminService.postNotice(postNoticeRequest));
    }

    /**
     * 이벤트 작성 API
     *
     * @param postEventRequest
     * @return
     * @throws IOException
     */
    @PostMapping("/event")
    public BaseResponse<PostEventResponse> postEvent(@Validated @ModelAttribute PostEventRequest postEventRequest) throws IOException {
        return new BaseResponse<>(adminService.postEvent(postEventRequest));
    }
}
