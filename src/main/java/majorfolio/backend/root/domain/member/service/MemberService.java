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
import majorfolio.backend.root.domain.member.entity.UserToken;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.UserTokenRepository;
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
    private final UserTokenRepository userTokenRepository;
    private final MemberRepository memberRepository;


    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    private final int randomCodeLength = 20;

    public MemberService(UserTokenRepository userTokenRepository, MemberRepository memberRepository) {
        this.userTokenRepository = userTokenRepository;
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
        Long userId;
        try {
            userId = memberRepository.findByUserToken(userTokenRepository.findById(kakaoId).get()).getId();
        }catch (NoSuchElementException e){
            userId = 0L;
        }catch (NullPointerException e){
            userId = 0L;
        }
        if(userId == 0){
            log.info("userId is null");
        }
        else{
            log.info("userId = {}", userId);
        }
        if(userId != 0){
            isMember = true;
        }

        Long expireAccessToken = Duration.ofHours(2).toMillis(); // 만료 시간 2시간
        String accessToken = JwtUtil.createAccessToken(userId, kakaoId, secretKey, expireAccessToken);

        Long expireRefreshToken = Duration.ofDays(14).toMillis(); // 만료 시간 2주
        String refreshToken = JwtUtil.createRefreshToken(secretKey, expireRefreshToken);

        //리프레쉬 토큰 db에 저장
        UserToken userToken = UserToken.builder()
                .id(kakaoId)
                .nonce(nonce)
                .state(state)
                .refreshToken(refreshToken)
                .build();
        userTokenRepository.save(userToken);

        return LoginResponse.builder()
                .isMember(isMember)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
