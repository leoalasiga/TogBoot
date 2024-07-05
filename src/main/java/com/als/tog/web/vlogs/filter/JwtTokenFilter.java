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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 检查是否是swagger地址和login接口
        if ("/togs/user/login".equals(request.getRequestURI()) || isSwaggerRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (response.getStatus()==200){
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateToken(jwt)) {
            Claims claims = JwtUtils.parseToken(jwt);
            System.out.println(claims);
            if(claims==null){
                throw new AuthenticationServiceException("无效的token");
            } else {
                filterChain.doFilter(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return requestPath.startsWith("/swagger-ui") ||
                requestPath.startsWith("/webjars") ||
                requestPath.startsWith("/v3/api-docs") ||
                requestPath.startsWith("/v2/api-docs") ||
                requestPath.startsWith("/swagger-resources") ||
                requestPath.startsWith("/configuration") ||
                requestPath.endsWith(".js") ||
                requestPath.endsWith(".css") ||
                requestPath.endsWith("favicon.ico");
    }

    // 解析jwtToken
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("token");
        return headerAuth;
    }
}