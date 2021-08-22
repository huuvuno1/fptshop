package nguyenhuuvu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {
    @RequestMapping()
    public String all(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("test", "vu");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "client/index";
    }
}
