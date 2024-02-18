package majorfolio.backend.root.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_FOUND_INFO_FROM_KAKAOID;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.NOT_FOUND_KAKAOID;

/**
 * token 값을 이용하여 멤버 정보 얻어오는 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberGlobalService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    public KakaoSocialLogin getMemberByToken(HttpServletRequest request){
        Object kakaoIdAttribute = request.getAttribute("kakaoId");
        System.out.println("kakaoIdAttribute = " + kakaoIdAttribute);

        if (kakaoIdAttribute == null) {
            // 카카오 아이디가 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_KAKAOID);
        }

        Long kakaoId = Long.parseLong(kakaoIdAttribute.toString());

        // 카카오 아이디에 해당하는 값 조회
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findById(kakaoId).orElse(null);
        if (kakaoSocialLogin == null || kakaoSocialLogin.getMember() == null || kakaoSocialLogin.getMember().getMajor1() == null) {
            // 카카오 아이디에 해당하는 값이 없을 때의 예외 처리 또는 메시지 전달 등의 처리
            throw new NotFoundException(NOT_FOUND_INFO_FROM_KAKAOID);
        }
        return kakaoSocialLogin;
    }

}
