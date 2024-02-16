package majorfolio.backend.root.global.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.MemberInfo;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.JwtUnsupportedTokenTypeException;
import majorfolio.backend.root.global.exception.NotFoundException;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.NoSuchElementException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

@RequiredArgsConstructor
@Slf4j
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final String secretKey;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(MemberInfo.class);
        boolean hasUserType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Long memberId;
        final String BASIC_TYPE_PREFIX = "Bearer";
        final String authorization = webRequest.getHeader("Authorization");
        final boolean isBasicAuthentication = authorization != null && authorization.toLowerCase().startsWith(BASIC_TYPE_PREFIX.toLowerCase());

        if (!isBasicAuthentication) {
            memberId = 0L;
            return memberId;
        }

        String token = authorization.substring(BASIC_TYPE_PREFIX.length()).trim();

        String tokenType = JwtUtil.getTokenHeader(token).get("typ").toString();

        if(!tokenType.equals("access_token")){
            //토큰 타입이 액세스 토큰이 아닐 경우
            throw new JwtInvalidException(INVALID_TOKEN);
        }

        if(JwtUtil.isExpired(token, secretKey)){
            // 토큰이 만료되었다면 예외 던지기
            throw new JwtExpiredException(EXPIRED_TOKEN);
        }

        //카카오Id 토큰으로부터 받아오기
        Long kakaoId = JwtUtil.getKaKaoId(token, secretKey);

        try {
            memberId = kakaoSocialLoginRepository.findById(kakaoId).get().getMember().getId();
        }catch (NoSuchElementException e){
            memberId = 0L;
        }

        return memberId;
    }
}
