package nguyenhuuvu.api.admin;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class ChatAdminController {


    @GetMapping("/chats")
    public ResponseEntity<?> fetchListChat() {
        List<MessageDTO> list = new ArrayList<>();
        // nho sort theo time
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
