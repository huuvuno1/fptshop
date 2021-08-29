package nguyenhuuvu.configuration;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.domain.StompPrincipal;
import nguyenhuuvu.exception.UserHandleException;
import nguyenhuuvu.utils.JwtTokenUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    final JwtTokenUtil jwtTokenUtil;
    boolean test = false;
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("Da vao custom");
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Cookie cookie = WebUtils.getCookie(servletRequest, "token");
        if (cookie != null) {
            try {
                if (jwtTokenUtil.isTokenExpired(cookie.getValue()))
                    throw new UserHandleException("Token expired");
                String username = jwtTokenUtil.getUsernameFromToken(cookie.getValue());
                return new StompPrincipal(username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if (!test) {
                test = true;
                return new StompPrincipal("okeem");
            }
            else
                return new StompPrincipal("okeem2");
            //throw new UserHandleException("User is not defined");
        }
        return null;
    }
}
