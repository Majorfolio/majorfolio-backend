package majorfolio.backend.root.domain.material.api;

import jakarta.servlet.http.Cookie;
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
    public MaterialListResponse getAllList(@CookieValue(name = "recent", required = false) String cookieValue) {
        System.out.println("cookieValue = " + cookieValue);
        return materialService.getAllList(cookieValue);
    }


}
