package lottery.draw.springboot.config;

import lottery.draw.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(jwtInterceptor())
                    .addPathPatterns("/**")    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
                    .excludePathPatterns("/**/login","/**/register","/user/");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
