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
import majorfolio.backend.root.domain.member.dto.SignupRequest;
import majorfolio.backend.root.domain.member.dto.SignupResponse;
import majorfolio.backend.root.domain.member.service.MemberService;
import majorfolio.backend.root.global.exception.MemberException;
import majorfolio.backend.root.global.response.BaseResponse;
import majorfolio.backend.root.global.util.BindingResultUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.INVALID_USER_VALUE;

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

    /**
     * 회원가입 api 컨트롤러
     * @param request
     * @param signupRequest
     * @param bindingResult
     * @return
     */
    @PostMapping("/signup")
    public BaseResponse<SignupResponse> signup(HttpServletRequest request, @Validated @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MemberException(INVALID_USER_VALUE, BindingResultUtil.getErrorMessages(bindingResult));
        }
        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());

        return new BaseResponse<>(memberService.signup(signupRequest, kakaoId));
    }
}
