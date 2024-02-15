package majorfolio.backend.root.config;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.global.interceptor.IdTokenInterceptor;
import majorfolio.backend.root.global.interceptor.RefreshTokenInterceptor;
import majorfolio.backend.root.global.interceptor.ServiceServerTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

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
                .addPathPatterns("/member/**", "/assignment/**", "/my/**", "/home/my/**")
                .excludePathPatterns("/member/login", "/member/remake/token", "/assignment/{materialId}/detail");
        registry.addInterceptor(refreshTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/remake/token");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://majorfolio.github.io/majorfolio-frontend", "https://majorfolio-frontend.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
