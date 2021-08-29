package nguyenhuuvu.api.web;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.domain.MyResponse;
import nguyenhuuvu.dto.MessageDTO;
import nguyenhuuvu.entity.User;
import nguyenhuuvu.service.UserService;
import nguyenhuuvu.service.WebSocketService;
import nguyenhuuvu.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {

    final SimpMessagingTemplate messagingTemplate;
    final UserService userService;
    final JwtTokenUtil jwtTokenUtil;
    final WebSocketService webSocketService;

    @GetMapping
    public ResponseEntity<?> fetchListMessage() {
        String username = null;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            System.out.println("Chua login");
            e.printStackTrace();
        }
        if (username == null)
            return new ResponseEntity<>(MyResponse.builder().status(406).time(new Date()).message("Chua login"), HttpStatus.NOT_ACCEPTABLE);
        else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    // khi nguoi dung muon chat voi admin thì se tạo 1 account // hạn sống 7 ngày
    @PostMapping (path = "/users/create")
    public ResponseEntity<?> createSessionUser(@RequestBody User user, HttpServletResponse response) {
        // Tạm bỏ qua validate
        userService.createSessionUser(user);
        String token = jwtTokenUtil.generateToken(user.getUsername());
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*7);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @MessageMapping
    public void te(@Payload String a) {
        System.out.println("oke : " + a);
    }


    @MessageMapping("/send-chat")
    public void chatTranfer(@Payload String message) {
        // message.setSender("Nguyen Huu Vu");
        // message.setTime(new Date());
        // // save
        // if (message.getReceiver().equals("admin"))
        //     webSocketService.sendNoticeToAdmin(message);
        // else
        System.out.println("send 1: " + message);
        messagingTemplate.convertAndSendToUser("okeem", "/chat/listen", message);
    }
}
