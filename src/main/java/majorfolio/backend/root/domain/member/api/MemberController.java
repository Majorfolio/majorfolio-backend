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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.member.dto.RemakeTokenResponse;
import majorfolio.backend.root.domain.member.dto.SignupRequest;
import majorfolio.backend.root.domain.member.dto.SignupResponse;
import majorfolio.backend.root.domain.member.dto.request.EmailCodeRequest;
import majorfolio.backend.root.domain.member.dto.request.EmailRequest;
import majorfolio.backend.root.domain.member.dto.response.EmailResponse;
import majorfolio.backend.root.domain.member.dto.response.LoginResponse;
import majorfolio.backend.root.domain.member.service.MemberService;
import majorfolio.backend.root.global.exception.EmailException;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.response.BaseResponse;
import majorfolio.backend.root.global.util.BindingResultUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.EMAIL_ERROR;
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
     * 이메일 인증 코드 보내기 컨트롤러
     * @param emailRequest
     * @return
     */
    @PostMapping("/school-email/code")
    public BaseResponse<EmailResponse> sendEmailAuthCode(@RequestBody @Validated EmailRequest emailRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new EmailException(EMAIL_ERROR, BindingResultUtil.getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(memberService.emailAuth(emailRequest));
    }

    /**
     * 이메일 인증 코드 대조 컨트롤러
     * @return
     */
    @GetMapping("/school-email/{emailId}/{code}")
    public BaseResponse<String> emailCodeCompare(@PathVariable(name = "emailId") Long emailId,
                                                 @PathVariable(name = "code") String code){
        return new BaseResponse<>(memberService.emailCodeCompare(emailId, code));
    }

    /**
     * 회원가입 API 컨트롤러
     * @param signupRequest
     * @return
     */
    @PostMapping("/signup")
    public BaseResponse<SignupResponse> signup(HttpServletRequest request, @Validated @RequestBody SignupRequest signupRequest
                                                , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new UserException(INVALID_USER_VALUE, BindingResultUtil.getErrorMessages(bindingResult));
        }

        Long kakaoId = Long.parseLong(request.getAttribute("kakaoId").toString());

        return new BaseResponse<>(memberService.signup(signupRequest, kakaoId));
    }

    /**
     * 토큰 재발급 API 컨트롤러
     * @param request
     * @return
     */
    @PostMapping("/remake/token")
    public BaseResponse<RemakeTokenResponse> remakeToken(HttpServletRequest request){
        return new BaseResponse<>(memberService.remakeToken(request));
    }

    /**
     * 닉네임 중복 검사 API
     * @param nickname
     * @return
     */
    @GetMapping("/check-nickname/{nickname}")
    public BaseResponse<String> checkNickname(@PathVariable(name = "nickname") String nickname){
        return new BaseResponse<>(memberService.checkNickname(nickname));
    }

    /**
     * 로그아웃 API
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request){
        return new BaseResponse<>(memberService.logout(request));
    }

}
