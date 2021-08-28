package nguyenhuuvu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {
    @RequestMapping()
    public String all(HttpServletRequest request, HttpServletResponse response) {
        return "client/index";
    }

    @CrossOrigin
    @RequestMapping("/test")
    public String test (HttpServletResponse response) {
        Cookie cookie = new Cookie("Test", "VKJK");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*60);
        response.addCookie(cookie);
        return "client/test";
    }
}
