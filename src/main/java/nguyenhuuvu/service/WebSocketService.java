package nguyenhuuvu.service;

import nguyenhuuvu.dto.MessageDTO;
import nguyenhuuvu.entity.User;

public interface WebSocketService {
    void sendNoticeToAdmin(MessageDTO message);
}
