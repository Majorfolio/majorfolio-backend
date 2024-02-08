package majorfolio.backend.root.domain.material.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.response.LibraryMaterialListResponse;
import majorfolio.backend.root.domain.material.service.LibraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/buy")
    public LibraryMaterialListResponse getBuyMaterialList(HttpServletRequest request){
        return libraryService.getBuyMaterialList(request);
    }
}
