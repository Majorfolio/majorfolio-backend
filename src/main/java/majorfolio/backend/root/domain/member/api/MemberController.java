/**
 * MemberController
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.service.MemberService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * /member 도메인으로 들어오는 요청 컨트롤러 정의
 * @author 김영록
 * @version 0.0.1
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 API요청 컨트롤러
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        String nonce = request.getHeader("nonce");
        String state = request.getHeader("state");
        return new BaseResponse<>(memberService.memberLogin(kakaoId,nonce,state));
    }
}
