package io.studio.authservice.controller;

import io.jsonwebtoken.Claims;
import io.studio.authservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/30 12:13
 */
@RestController("/api")
public class AuthController {


    @GetMapping("/validate")
    public String JwtAuthByAPI(HttpServletRequest request) {

        String token = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            boolean isValid = JwtUtils.validateToken(token);
            if (isValid) {
                Claims claims = JwtUtils.getClaimsFromToken(token);
                String username = claims.getSubject();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                return null;
            }
        }
        return JwtUtils.getUsernameFromToken(token);
    }
}
