package nguyenhuuvu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nguyenhuuvu.domain.JwtRequest;
import nguyenhuuvu.domain.MyResponse;
import nguyenhuuvu.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    final AuthenticationManagerBuilder authenticationManagerBuilder;
    final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                jwtRequest.getUsername(),
                jwtRequest.getPassword()
        );
        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            log.warn("Login with username: {} -> fail", jwtRequest.getUsername());
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(authentication);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            return new ResponseEntity<>(
                    MyResponse.builder().status(200).message("Login Success!").time(new Date()).build(),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(
                    MyResponse.builder().status(406).message("Login Fail!").time(new Date()).build(),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
