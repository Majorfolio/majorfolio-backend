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
import majorfolio.backend.root.domain.material.dto.response.MyMaterialResponse;
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
@CrossOrigin(originPatterns = {"http://localhost:3000", "https://majorfolio.github.io/majorfolio-frontend", "https://vercel.com/algoorgoals-projects/majorfolio-frontend"})@RequiredArgsConstructor
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

    /**
     * 내가 북마크 한거 모아보기(페이징 처리 포함)
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/bookmark")
    public BaseResponse<MyMaterialResponse> showBookmarkList(@RequestParam(name = "page") int page,
                                                             @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                             HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(myService.showBookmarkList(page, pageSize, kakaoId));
    }

    @GetMapping("/like")
    public BaseResponse<MyMaterialResponse> showLikeList(@RequestParam(name = "page") int page,
                                                         @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                         HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());
        return new BaseResponse<>(myService.showLikeList(page, pageSize, kakaoId));
    }
}
