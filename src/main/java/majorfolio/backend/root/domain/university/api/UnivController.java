package majorfolio.backend.root.domain.university.api;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.university.service.UnivService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/univ")
public class UnivController {
    private final UnivService univService;

    //나중에 이거 어드민만 가능하도록 해야 함
    @PostMapping("/fill")
    public String fillUniv(@RequestParam("file") MultipartFile file){
        return univService.fillUniv(file);
    }
}
