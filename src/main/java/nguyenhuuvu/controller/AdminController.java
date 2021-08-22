package nguyenhuuvu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("")
    public String all() {
        return "admin/index";
    }

    @GetMapping ("/login")
    public String loginPage() {
        return "admin/login";
    }
    
    @GetMapping("/chat")
    public String chatPage() {
        return "admin/chat";
    }
}
