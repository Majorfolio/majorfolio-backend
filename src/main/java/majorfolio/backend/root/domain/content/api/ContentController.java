package majorfolio.backend.root.domain.content.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.content.dto.response.ContentsListResponse;
import majorfolio.backend.root.domain.content.service.ContentService;
import org.springframework.web.bind.annotation.*;

/**
 * /home에서 배너에 관한 도메인을 요청하는 컨트롤러 정의
 * @author 김태혁
 * @version 0.0.1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@CrossOrigin(originPatterns = "http://localhost:3000")
public class ContentController {

    private final ContentService contentService;

    /**
     * /home/banner api 요청을 수행
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("/banner")
    public ContentsListResponse getBannerLists() {
        return contentService.banner_contents();
    }
}
