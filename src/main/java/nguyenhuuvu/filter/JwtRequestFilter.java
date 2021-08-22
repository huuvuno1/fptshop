package nguyenhuuvu.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nguyenhuuvu.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie != null)
            token = cookie.getValue();
        String username = null;
        boolean expried = false;
        if (token != null) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                expried = jwtTokenUtil.isTokenExpired(token);
            }
            catch (Exception e) {
                log.warn("Token failed");
            }
            if (username != null && !expried && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = jwtTokenUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
