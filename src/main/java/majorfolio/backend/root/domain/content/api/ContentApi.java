package majorfolio.backend.root.domain.content.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.content.dto.response.ContentsListResponse;
import majorfolio.backend.root.domain.content.service.ContentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class ContentApi {

    private final ContentService contentService;

    @GetMapping("/banner")
    public ContentsListResponse getBannerLists() {
        return contentService.banner_contents();
    }
}
