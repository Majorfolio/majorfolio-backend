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
import majorfolio.backend.root.domain.member.dto.request.EmailCodeRequest;
import majorfolio.backend.root.domain.member.dto.request.EmailRequest;
import majorfolio.backend.root.domain.member.dto.response.EmailResponse;
import majorfolio.backend.root.domain.member.dto.response.LoginResponse;
import majorfolio.backend.root.domain.member.service.MemberService;
import majorfolio.backend.root.global.exception.EmailException;
import majorfolio.backend.root.global.response.BaseResponse;
import majorfolio.backend.root.global.util.BindingResultUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.EMAIL_ERROR;

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

    @GetMapping("/school-email/code")
    public BaseResponse<String> emailCodeCompare(@RequestBody EmailCodeRequest emailCodeRequest){
        return new BaseResponse<>(memberService.emailCodeCompare(emailCodeRequest));
    }
}
