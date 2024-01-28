package majorfolio.backend.root.config;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.global.interceptor.IdTokenInterceptor;
import majorfolio.backend.root.global.interceptor.ServiceServerTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final IdTokenInterceptor idTokenInterceptor;
    private final ServiceServerTokenInterceptor serviceServerTokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/login");
        registry.addInterceptor(serviceServerTokenInterceptor)
                .order(1)
                .addPathPatterns("/member/**")
                .excludePathPatterns("/member/login");
    }
}
