package majorfolio.backend.root.domain.material.api;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.service.MaterialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;


    @GetMapping("all/univ")
    public MaterialListResponse getAllMaterialList(@CookieValue(name = "recent", required = false) String cookieValue) {
         return materialService.getAllList(cookieValue);
    }

    @GetMapping("my/univ")
    public MaterialListResponse getUnivMaterialList(HttpServletRequest request, @CookieValue(name = "recent", required = false) String cookieValue) {
        return materialService.getUnivList(request, cookieValue);
    }

    @GetMapping("my/major")
    public MaterialListResponse getMajorMaterialList(HttpServletRequest request, @CookieValue(name = "recent", required = false) String cookieValue) {
        return materialService.getMajorList(request, cookieValue);
    }
}
