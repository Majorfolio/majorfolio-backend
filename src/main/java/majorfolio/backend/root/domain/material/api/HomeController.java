package majorfolio.backend.root.domain.material.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.material.dto.response.MaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.NameMaterialListResponse;
import majorfolio.backend.root.domain.material.dto.response.SingleMaterialListResponse;
import majorfolio.backend.root.domain.material.service.MaterialAllListService;
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
public class HomeController {

    private final MaterialService materialService;
    private final MaterialAllListService materialAllListService;

    /**
     * /home/all/univ로 get 요청이 왔을 때 모든학교의 자료들을 전달
     * @author 김태혁
     * @version 0.0.1
     */
    @GetMapping("all/univ")
    public MaterialListResponse getAllMaterialList(@CookieValue(name = "recent", required = false) String cookieValue) {
         return materialService.getAllList(cookieValue);
    }

    @GetMapping("all/univ/newly-upload")
    public SingleMaterialListResponse getAllUnivNewlyupload(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return materialAllListService.getAllUnivUploadList(page, pageSize);
    }

    @GetMapping("all/univ/likes")
    public SingleMaterialListResponse getAllUnivLike(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return materialAllListService.getAllUnivLike(page, pageSize);
    }

    @GetMapping("all/subject")
    public NameMaterialListResponse getAllSubjectNewly(@RequestParam(name = "materialID") Long materialID,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return materialAllListService.getAllSubjectNew(page, pageSize, materialID);
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

    @GetMapping("my/univ/newly-upload")
    public SingleMaterialListResponse getMyUnivNewlyupload(@RequestParam(name = "page") int page,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                           HttpServletRequest request){
        return materialAllListService.getMyUnivUploadList(page, pageSize, request);
    }

    @GetMapping("my/univ/likes")
    public SingleMaterialListResponse getMyUnivLike(@RequestParam(name = "page") int page,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                    HttpServletRequest request){
        return materialAllListService.getMyUnivLike(page, pageSize, request);
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

    @GetMapping("my/major/newly-upload")
    public SingleMaterialListResponse getMyMajorNewlyupload(@RequestParam(name = "page") int page,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                           HttpServletRequest request){
        return materialAllListService.getMyMajorUploadList(page, pageSize, request);
    }

    @GetMapping("my/major/likes")
    public SingleMaterialListResponse getMyMajorLike(@RequestParam(name = "page") int page,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                    HttpServletRequest request){
        return materialAllListService.getMyMajorLike(page, pageSize, request);
    }


}
