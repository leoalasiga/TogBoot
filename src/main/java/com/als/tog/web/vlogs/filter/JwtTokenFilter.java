package com.als.tog.web.vlogs.filter;

import com.als.tog.utils.JwtUtils;
import com.als.tog.web.vlogs.entity.UserInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dkw
 */
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    // 在这里不进行筛选，所有的接口最终都会放行，在filter中只进行对token的解析和用户身份验证
    // 比如token无效后，抛出token无效的错误
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateToken(jwt)) {
            Claims claims = JwtUtils.parseToken(jwt);
            if(claims==null){
                throw new AuthenticationServiceException("无效的token");
            } else {
                filterChain.doFilter(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }


    // 解析jwtToken
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("token");
        return headerAuth;
    }
}