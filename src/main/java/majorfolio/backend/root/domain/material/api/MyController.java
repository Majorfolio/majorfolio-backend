/**
 * MyController
 *
 * 2024.02.05
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.ProfileResponse;
import majorfolio.backend.root.domain.material.service.MyService;
import majorfolio.backend.root.domain.material.service.ProfileService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

/**
 * /My 요청에 관한 컨트롤러 정의
 *
 * @author 김영록
 * @version 0.0.1
 */
@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;
    private final ProfileService profileService;

    /**
     * 마이페이지 정보 요청 API
     *
     * @param request
     * @return
     */
    @GetMapping("/")
    public ProfileResponse getSellerProfile(HttpServletRequest request){
        return profileService.getUserProfile(request);
    }

    /**
     * 좋아요 API 요청 처리
     * @param materialId
     * @param request
     * @return
     */
    @PostMapping("/{materialId}/like")
    public BaseResponse<String> like(@PathVariable(name = "materialId") Long materialId,
                                     HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(myService.like(materialId, kakaoId));
    }

    /**
     * 북마크 API요청 처리
     * @param materialId
     * @param request
     * @return
     */
    @PostMapping("/{materialId}/bookmark")
    public BaseResponse<String> bookmark(@PathVariable(name = "materialId") Long materialId,
                                     HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(myService.bookmark(materialId, kakaoId));
    }
}
