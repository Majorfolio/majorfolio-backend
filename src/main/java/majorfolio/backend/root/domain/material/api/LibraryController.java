package majorfolio.backend.root.domain.material.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.LibraryMaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.UploadMaterialListResponse;
import majorfolio.backend.root.domain.material.service.LibraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 자료함에 대한 Controller
 */
@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {
    private final LibraryService libraryService;

    /**
     * 구매한 자료들을 인피니티 페이지로 전달받는 메서드
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/buy")
    public LibraryMaterialListResponse getBuyMaterialList(@RequestParam(name = "page") int page,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                          HttpServletRequest request){
        return libraryService.getBuyMaterialList(page, pageSize, request);
    }

    /**
     * 업로드한 자료들을 인피니티 페이지로 전달하는 메서드
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/upload")
    public UploadMaterialListResponse getUploadMaterialList(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                            HttpServletRequest request){
        return libraryService.getUploadMaterialList(page, pageSize, request);
    }
}
