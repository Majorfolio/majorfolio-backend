package majorfolio.backend.root.domain.member.service;

import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.dto.LoginResponse;
import majorfolio.backend.root.domain.member.entity.UserToken;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.domain.member.repository.UserTokenRepository;
import majorfolio.backend.root.global.util.JwtUtil;
import majorfolio.backend.root.global.util.RandomCodeUtil;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class MemberService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    private final UserTokenRepository userTokenRepository;
    private final MemberRepository memberRepository;

    private final int randomCodeLength = 20;

    public MemberService(UserTokenRepository userTokenRepository, MemberRepository memberRepository) {
        this.userTokenRepository = userTokenRepository;
        this.memberRepository = memberRepository;
    }

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
