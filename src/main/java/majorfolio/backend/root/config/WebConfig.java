package majorfolio.backend.root.config;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.global.interceptor.IdTokenInterceptor;
import majorfolio.backend.root.global.interceptor.RefreshTokenInterceptor;
import majorfolio.backend.root.global.interceptor.ServiceServerTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final IdTokenInterceptor idTokenInterceptor;
    private final ServiceServerTokenInterceptor serviceServerTokenInterceptor;
    private final RefreshTokenInterceptor refreshTokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/login");
        registry.addInterceptor(serviceServerTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/**", "/assignment/**", "/my/**", "/home/my/**", "/library/**")
                .excludePathPatterns("/member/login", "/member/remake/token", "/assignment/{materialId}/detail");
        registry.addInterceptor(refreshTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/remake/token");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 출처
                .allowedMethods("GET", "POST", "PUT") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }
}
