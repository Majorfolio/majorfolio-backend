package majorfolio.backend.root.domain.material.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.NameMaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.SellerMaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.SellerProfileResponse;
import majorfolio.backend.root.domain.material.service.MaterialAllListService;
import majorfolio.backend.root.domain.material.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final MaterialAllListService materialAllListService;
    private final ProfileService profileService;

    @GetMapping("/assignment")
    public SellerMaterialListResponse getAllSellerList(@RequestParam(name = "nickName") String nickName,
                                                       @RequestParam(name = "page") int page,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return materialAllListService.getSellerList(page, pageSize, nickName);
    }

    @GetMapping("/profile")
    public SellerProfileResponse getSellerProfile(@RequestParam(name = "nickName") String nickName){
        return profileService.getSellerProfile(nickName);
    }
}
