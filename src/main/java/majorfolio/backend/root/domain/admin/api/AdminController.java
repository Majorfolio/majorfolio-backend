package majorfolio.backend.root.domain.admin.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.admin.dto.request.PostEventRequest;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostEventResponse;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.service.AdminService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/notice")
    public BaseResponse<PostNoticeResponse> postNotice(@Validated @ModelAttribute PostNoticeRequest postNoticeRequest) throws IOException {
        return new BaseResponse<>(adminService.postNotice(postNoticeRequest));
    }

    @PostMapping("/event")
    public BaseResponse<PostEventResponse> postEvent(@Validated @ModelAttribute PostEventRequest postEventRequest) throws IOException {
        return new BaseResponse<>(adminService.postEvent(postEventRequest));
    }
}
