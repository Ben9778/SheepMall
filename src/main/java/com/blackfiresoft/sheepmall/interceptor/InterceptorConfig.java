package com.blackfiresoft.sheepmall.interceptor;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private AuthInterceptor mvcInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mvcInterceptor)
                .addPathPatterns("/57596/**","/57597/**","/57598/**","/57599/**","/57600/**","/57601/**","/57602/**","/57603/**","/57604/**")
                .excludePathPatterns("/57596/login/u", "/57596/login/e");
    }
}
