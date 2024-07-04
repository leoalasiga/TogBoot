package com.als.tog.web.vlogs.filter;

import com.als.tog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";
    private final JwtUtils jwtUtils;

    public JwtTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if the request is for a Swagger path
        if ("/togs/user/login".equals(request.getRequestURI()) || isSwaggerRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Parse the JWT from the request header
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateToken(jwt)) {
            // Get the username from the token and load the user details
            String username = jwtUtils.getUsernameFromToken(jwt);

            // Create an authenticated token and set it in the SecurityContextHolder
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

    private boolean isLoginInterface(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return requestPath.endsWith("login");
    }

    // 解析jwtToken
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(JWT_TOKEN_PREFIX)) {
            return headerAuth.substring(JWT_TOKEN_PREFIX.length());
        }
        return null;
    }
}