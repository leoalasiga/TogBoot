package com.als.tog.config;

import com.als.tog.web.vlogs.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dkw
 */
@Configuration //注解表明这是一个配置类，Spring 将从这个类中读取 Bean 的定义。
@EnableWebSecurity //注解会启用 Spring Security 的 web 安全支持
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 创建并返回 BCrypt 密码编码器实例
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetailsManager users = new InMemoryUserDetailsManager(); // 内置的用户详情服务实现，用于在内存中存储用户信息
        users.createUser(User.builder()
                .username("root") // 设置用户的用户名
                .password(passwordEncoder.encode("Aa147258.")) // 设置用户的访问密码
                .roles("USER") // 设置用户的角色
                .build());
        return users;
    }

    /**
     * 定义安全过滤器链的行为
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize // 配置 HTTP 请求授权
                        .antMatchers("/admin").hasRole("ADMIN") //配置对 /admin 路径的请求需要有 "ADMIN" 角色才能访问。
                        .antMatchers("/",
                                "/*.html",
                                "/home",
                                "/**/*.html",
                                "/v2/api-docs",
                                "/configuration/ui",
                                "/swagger-resources",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**").permitAll() //配置对当前路径的请求不需要认证即可访问。
                        .antMatchers("/user/login").permitAll()
                        .anyRequest().authenticated() // 配置所有未被明确授权的请求都需要经过身份验证
                )
                .logout((logout) -> logout.permitAll())// 对于登出路径的所有请求都不进行验证
                .csrf().disable() // 禁用 CSRF 保护，Swagger UI 不支持 CSRF 保护
                .cors().disable() // 禁用 CORS 支持
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().and().csrf().disable();

        return http.build();
    }

}

