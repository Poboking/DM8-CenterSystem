package io.studio.authservice.filter;

import io.jsonwebtoken.Claims;
import io.studio.authservice.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 2:57
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {




    /**
     * 重写doFilterInternal方法，该方法在每个请求到达时被调用。
     * 用于过滤请求，验证JWT令牌
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())){
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            boolean isValid = JwtUtils.validateToken(token);

            if (isValid) {
                Claims claims = JwtUtils.getClaimsFromToken(token);
                String username = claims.getSubject();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}