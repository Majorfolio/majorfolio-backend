package majorfolio.backend.root.global.interceptor;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.entity.UserToken;
import majorfolio.backend.root.domain.member.repository.UserTokenRepository;
import majorfolio.backend.root.global.provider.CustomJwkProvider;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class IdTokenInterceptor implements HandlerInterceptor {
    private static final String BASIC_TYPE_PREFIX = "Bearer";
    private static final String DELIMITER = ":";

    private final UserTokenRepository userTokenRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");
        final boolean isBasicAuthentication = authorization != null && authorization.toLowerCase().startsWith(BASIC_TYPE_PREFIX.toLowerCase());

        if (!isBasicAuthentication) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String idToken = authorization.substring(BASIC_TYPE_PREFIX.length()).trim();

        Map<String, Object> payload = JwtUtil.getTokenPayload(idToken);

        String nonce = request.getHeader("nonce");

        //페이로드 검증
        if(!validatePayload(payload, nonce)){
            return false;
        }

        //서명 검증
        if(!validateSign(idToken)){
            return false;
        }

        //카카오id 가져오기
        Long kakao_id = Long.parseLong(payload.get("sub").toString());
        log.info("kakao_id = {}", kakao_id);
        request.setAttribute("kakao_id", kakao_id);
        return true;
    }

    public boolean validatePayload(Map<String, Object> payload, String nonce){
        log.info("start validatePayload");
        // 페이로드의 iss 값이 https://kauth.kakao.com와 일치하는지 확인
        if(!payload.get("iss").equals("https://kauth.kakao.com")){
            log.error("uri 불일치");
            return false;
        }
        // 페이로드의 aud 값이 서비스 앱 키와 일치하는지 확인
        if(!payload.get("aud").equals(client_id)){
            log.error("client_id 불일치");
            return false;
        }

        //페이로드의 exp 값이 현재 UNIX 타임스탬프(Timestamp)보다 큰 값인지 확인(ID 토큰이 만료되지 않았는지 확인)
        Long exp = Long.parseLong(payload.get("exp").toString());
        Long now = System.currentTimeMillis();

        log.info("exp = {}", exp);
        log.info("now = {}", now);

        if(exp > now){
            log.error("유효기간 만료");
            return false;
        }

        if(!payload.get("nonce").equals(nonce)){
            log.error("nonce값 불일치");
            return false;
        }


        return true;
    }

    public Boolean validateSign(String idToken) throws JwkException {
        log.info("사인 검증");
        // 1. 검증없이 디코딩
        DecodedJWT jwtOrigin = JWT.decode(idToken);

// 2. 공개키 프로바이더 준비
        JwkProvider provider = CustomJwkProvider.getInstance();
        Jwk jwk = provider.get(jwtOrigin.getKeyId());

// 3. 검증 및 디코딩
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(idToken);

        return true;
    }
}
