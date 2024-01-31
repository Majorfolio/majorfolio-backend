package majorfolio.backend.root.domain.material.api;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.service.MaterialService;
import org.springframework.web.bind.annotation.*;
/**
 * /home으로 시작하여 과제들을 조회하는 도메인을 관리하는 컨트롤러
 * @author 김태혁
 * @version 0.0.1
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    /**
     * /home/all/univ로 get 요청이 왔을 때 모든학교의 자료들을 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("all/univ")
    public MaterialListResponse getAllMaterialList(@CookieValue(name = "recent", required = false) String cookieValue) {
         return materialService.getAllList(cookieValue);
    }

    /**
     * /home/my/univ로 get 요청이 왔을 때 내 학교의 자료들을 필터링 하여 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("my/univ")
    public MaterialListResponse getUnivMaterialList(HttpServletRequest request, @CookieValue(name = "recent", required = false) String cookieValue) {
        return materialService.getUnivList(request, cookieValue);
    }

    /**
     * /home/my/major로 get 요청이 왔을 때 내 학과의 자료들을 필터링 하여 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("my/major")
    public MaterialListResponse getMajorMaterialList(HttpServletRequest request, @CookieValue(name = "recent", required = false) String cookieValue) {
        return materialService.getMajorList(request, cookieValue);
    }

}
