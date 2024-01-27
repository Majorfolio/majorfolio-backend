/**
 * MemeberService
 *
 * 0.0.1
 *
 * 2024.01.23
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.member.service;

import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.NoSuchElementException;

/**
 * MemeberController에서 수행되는 서비스 동작을 정의한 클래스
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
public class MemberService {
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final MemberRepository memberRepository;


    @Value("${jwt.secret}")
    private String secretKey;

    public MemberService(KakaoSocialLoginRepository kakaoSocialLoginRepository, MemberRepository memberRepository) {
        this.kakaoSocialLoginRepository = kakaoSocialLoginRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * /member/login으로 요청시에 수행될 서비스 로직을 정의한 메소드
     * 현재 회원인지 아닌지값, 액세스 토큰, 리프레쉬 토큰을 반환해준다.
     * @param kakaoId
     * @param nonce
     * @param state
     * @return
     */
    public LoginResponse memberLogin(Long kakaoId, String nonce, String state){
        Boolean isMember = false;
        Long memberId;
        KakaoSocialLogin kakaoSocialLogin = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId);
        if(kakaoSocialLogin == null){
            // 카카오 소셜 로그인 객체가 없으면 만들어서 DB에 저장한다.
            kakaoSocialLogin = KakaoSocialLogin.builder().build();
            kakaoSocialLoginRepository.save(kakaoSocialLogin);
        }
        try {
            //memberId = memberRepository.findByUserToken(kakaoSocialLoginRepository.findById(kakaoId).get()).getId();
            memberId = kakaoSocialLoginRepository.findByKakaoNumber(kakaoId).getMember().getId();
        }catch (NoSuchElementException e){
            memberId = 0L;
        }catch (NullPointerException e){
            memberId = 0L;
        }
        if(memberId == 0){
            log.info("memberId is null");
        }
        else{
            log.info("memberId = {}", memberId);
        }
        if(memberId != 0){
            isMember = true;
        }

        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간
        String accessToken = JwtUtil.createAccessToken(memberId, kakaoId, secretKey, expireAccessToken);

        Long expireRefreshToken = Duration.ofDays(14).toMillis(); // 만료 시간 2주
        String refreshToken = JwtUtil.createRefreshToken(secretKey, expireRefreshToken);

        //리프레쉬 토큰 db에 저장
        kakaoSocialLogin.setKakaoNumber(kakaoId);
        kakaoSocialLogin.setState(state);
        kakaoSocialLogin.setNonce(nonce);
        kakaoSocialLogin.setRefreshToken(refreshToken);
        kakaoSocialLoginRepository.save(kakaoSocialLogin);

        return LoginResponse.builder()
                .isMember(isMember)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
