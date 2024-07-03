package com.als.tog.web.vlogs.filter;

import com.als.tog.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author dkw
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    String[] swaggerPaths = {"/swagger-ui.html", "/doc.html", "/webjars/**", "/v3/api-docs/**", "/v2/api-docs", "/swagger-resources", "/configuration/**", ".js", ".css","favicon.ico"};


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if the request is for a Swagger path
        String docPath = request.getRequestURI();
        System.out.println(docPath);
        if (!Arrays.stream(swaggerPaths).anyMatch(path -> request.getRequestURI().endsWith(path))) {
            // JWT token validation logic here
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
//                String token = headerAuth.substring(7);
//                if (!jwtUtils.validateToken(token)) {
//                    // Handle invalid token
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
//                    return;
//                }
//
//                // Load user details from token and set in SecurityContext
//                UserDetails userDetails = jwtUtils.getUserDetailsFromToken(token);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Handle missing token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
                return;
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
    // 解析token
//    private String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("token");
//
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtTokenPrefix)) {
//            return headerAuth.substring(jwtTokenPrefix.length(), headerAuth.length());
//        }
//
//        return null;
//    }
}