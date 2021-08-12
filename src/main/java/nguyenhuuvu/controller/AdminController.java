package nguyenhuuvu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/{url}.html")
    public String all(@PathVariable("url") String url) {
        System.out.println(url);
        return "";
    }
}
