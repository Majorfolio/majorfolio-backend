package majorfolio.backend.root.domain.material.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.ProfileResponse;
import majorfolio.backend.root.domain.material.dto.response.SellerMaterialListResponse;
import majorfolio.backend.root.domain.material.service.MaterialAllListService;
import majorfolio.backend.root.domain.material.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@CrossOrigin(originPatterns = "http://localhost:3000")
@RequiredArgsConstructor
public class SellerController {
    private final MaterialAllListService materialAllListService;
    private final ProfileService profileService;

    /**
     * /seller/assignment로 get 요청이 들어왔을 때 판매자의 모든 자료를 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("/assignment")
    public SellerMaterialListResponse getAllSellerList(@RequestParam(name = "nickName") String nickName,
                                                       @RequestParam(name = "page") int page,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return materialAllListService.getSellerList(page, pageSize, nickName);
    }

    /**
     * /seller/profile get 요청이 들어왔을 때 판매자 프로필을 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("/profile")
    public ProfileResponse getSellerProfile(@RequestParam(name = "memberId") Long memberId){
        return profileService.getProfile(memberId);
    }
}
