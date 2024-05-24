package com.als.tog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author dkw
 */
@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    private CrossInterceptor crossInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加 CrossInterceptor 到拦截器链中
        registry.addInterceptor(crossInterceptor)
                // 指定拦截的路径，这里示例为拦截所有请求，根据需要可具体配置
                .addPathPatterns("/**");
    }
}
