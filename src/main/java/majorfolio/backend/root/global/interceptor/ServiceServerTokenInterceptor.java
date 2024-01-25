/**
 * ServiceServerTokenInterceptor
 *
 * 0.0.1
 *
 * 2024.01.25
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.EXPIRED_TOKEN;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.INVALID_TOKEN;

/**
 * 서비스에서 발행한 JWT가 유효한지 사전에 체크하는 interceptor 구현
 *
 * @author 김영록
 * @version 0.0.1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceServerTokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = request.getAttribute("token").toString();
        String tokenType = JwtUtil.getTokenHeader(userToken).get("typ").toString();
        log.info(tokenType);

        if(!tokenType.equals("accessToken")){
            //토큰 타입이 액세스 토큰이 아닐 경우
            throw new JwtInvalidException(INVALID_TOKEN);
        }

        if(JwtUtil.isExpired(userToken, secretKey)){
            // 토큰이 만료되었다면 예외 던지기
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }

        //카카오Id 토큰으로부터 받아오기
        Long kakaoId = JwtUtil.getKaKaoId(userToken, secretKey);
        request.setAttribute("kakaoId",kakaoId);

        return true;
    }
}
