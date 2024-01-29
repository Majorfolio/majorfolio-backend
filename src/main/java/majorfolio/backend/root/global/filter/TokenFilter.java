/**
 * TokenFilter
 *
 * 0.0.1
 *
 * 2024.01.24
 *
 * Majorfolio
 */
package majorfolio.backend.root.global.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import majorfolio.backend.root.global.exception.JwtUnsupportedTokenTypeException;

import java.io.IOException;

import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.UNSUPPORTED_TOKEN_TYPE;

/**
 * authorization헤더에 입력된 토큰이 Bearer형식인지 filter에서 확인
 * 이걸 interceptor에서도 할 수 있는데 굳이 분리한 이유
 * : IdToken인터셉터와 서비스토큰 인터셉터가 토큰이 Bearer방식인지 아닌지 체크하는건 공통된 사항임
 * 그래서 그 공통된 사항을 filter로 따로 빼내는게 코드의 중복성을 해소하는 것이라고 생각이 들었음
 *
 * @author 김영록
 * @version 0.0.1
 */
public class TokenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

// "/home/banner"는 토큰이 없어도 가능하도록 변경
@Override
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    final String BASIC_TYPE_PREFIX = "Bearer";
    final HttpServletRequest request = (HttpServletRequest) servletRequest;
    final String path = request.getRequestURI();

    if (!path.equals("/home/banner") && !request.getMethod().equals("GET")) {
        final String authorization = request.getHeader("Authorization");
        final boolean isBasicAuthentication = authorization != null && authorization.toLowerCase().startsWith(BASIC_TYPE_PREFIX.toLowerCase());

        if (!isBasicAuthentication) {
            throw new JwtUnsupportedTokenTypeException(UNSUPPORTED_TOKEN_TYPE);
        }

        String token = authorization.substring(BASIC_TYPE_PREFIX.length()).trim();
        request.setAttribute("token", token);
    }

    filterChain.doFilter(request, servletResponse);
}



    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
