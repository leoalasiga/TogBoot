package com.als.tog.web.vlogs.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author dkw
 * springSecurity用户登录校验类
 */
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里可以查询数据库或其他数据源来获取用户信息
        if ("user".equals(username)) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("当前用户不存在: " + username);
        }
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
