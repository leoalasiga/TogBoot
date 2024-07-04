package com.als.tog.config;

import com.als.tog.utils.JwtUtils;
import com.als.tog.web.vlogs.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
                //静态文件允许访问
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers(  "/swagger-ui/**").permitAll()
                // 放行
                .antMatchers("/**/*.html").permitAll()
                .antMatchers( "/login","css/**","/js/**","/image/*").permitAll()
                .antMatchers("/togs/user/login").permitAll()
                .anyRequest().authenticated() // 配置所有未被明确授权的请求都需要经过身份验证
                )
                .csrf().disable(); // 禁用 CSRF 保护，Swagger UI 不支持 CSRF 保护

        http.addFilterAfter(new JwtTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
