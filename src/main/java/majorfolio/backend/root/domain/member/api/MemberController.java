package majorfolio.backend.root.domain.member.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.service.MemberService;
import majorfolio.backend.root.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(HttpServletRequest request){
        Long kakaoId = Long.parseLong(request.getAttribute("kakao_id").toString());
        String nonce = request.getHeader("nonce");
        String state = request.getHeader("state");
        return new BaseResponse<>(memberService.memberLogin(kakaoId,nonce,state));
    }
}
