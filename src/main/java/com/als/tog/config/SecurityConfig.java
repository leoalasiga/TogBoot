package com.als.tog.config;

import com.als.tog.utils.JwtUtils;
import com.als.tog.web.vlogs.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dkw
 */
@Configuration //注解表明这是一个配置类，Spring 将从这个类中读取 Bean 的定义。
@EnableWebSecurity //注解会启用 Spring Security 的 web 安全支持
public class SecurityConfig {

    private JwtUtils jwtUtils;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 定义安全过滤器链的行为
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize // 配置 HTTP 请求授权
                        .antMatchers("/admin").hasRole("ADMIN") //配置对 /admin 路径的请求需要有 "ADMIN" 角色才能访问。
                        .antMatchers("/togs/doc.html",
                                "/*.html",
                                "/home",
                                "/togs/doc.html",
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/swagger-resources",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**").permitAll()
                        .antMatchers("togs/user/login").permitAll()
                        .anyRequest().authenticated() // 配置所有未被明确授权的请求都需要经过身份验证
                )
                .logout((logout) -> logout.permitAll())// 对于登出路径的所有请求都不进行验证
                .csrf().disable() // 禁用 CSRF 保护，Swagger UI 不支持 CSRF 保护
                .addFilterBefore(new JwtTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}

