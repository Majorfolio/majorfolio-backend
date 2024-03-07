package majorfolio.backend.root.global.argument_resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.member.entity.EmailDB;
import majorfolio.backend.root.domain.member.entity.KakaoSocialLogin;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.repository.EmailDBRepository;
import majorfolio.backend.root.domain.member.repository.KakaoSocialLoginRepository;
import majorfolio.backend.root.domain.member.repository.MemberRepository;
import majorfolio.backend.root.global.argument_resolver.custom_annotation.TokenInfo;
import majorfolio.backend.root.global.exception.JwtExpiredException;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.UserException;
import majorfolio.backend.root.global.util.JwtUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.NoSuchElementException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

@RequiredArgsConstructor
@Slf4j
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(TokenInfo.class);
        boolean hasUserType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return Long.parseLong(webRequest.getAttribute("memberId", RequestAttributes.SCOPE_REQUEST).toString());
    }
}
