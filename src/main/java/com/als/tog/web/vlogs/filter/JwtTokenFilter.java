package com.als.tog.web.vlogs.filter;

import com.als.tog.utils.JwtUtils;
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
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String jwtHeader;
    private final String jwtTokenPrefix;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.jwtHeader = "Authorization";
        this.jwtTokenPrefix = "Bearer ";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 请求的时候要把token带过来
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // 根据token获取到用户名
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // 根据用户名获取到用户的详细信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 将用户信息存储到Authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtHeader);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtTokenPrefix)) {
            return headerAuth.substring(jwtTokenPrefix.length(), headerAuth.length());
        }

        return null;
    }
}