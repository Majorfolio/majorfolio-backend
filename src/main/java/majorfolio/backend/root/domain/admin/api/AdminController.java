package majorfolio.backend.root.domain.admin.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.admin.dto.request.PostNoticeRequest;
import majorfolio.backend.root.domain.admin.dto.response.PostNoticeResponse;
import majorfolio.backend.root.domain.admin.service.AdminService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/notice")
    public BaseResponse<PostNoticeResponse> postNotice(@Validated @ModelAttribute PostNoticeRequest postNoticeRequest){
        return new BaseResponse<>(adminService.postNotice(postNoticeRequest));
    }
}
