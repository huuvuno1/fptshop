package nguyenhuuvu.service.impl;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.dto.MessageDTO;
import nguyenhuuvu.entity.Role;
import nguyenhuuvu.entity.User;
import nguyenhuuvu.enums.RoleType;
import nguyenhuuvu.repository.RoleRepository;
import nguyenhuuvu.repository.UserRepository;
import nguyenhuuvu.service.WebSocketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImp implements WebSocketService {
    final SimpMessagingTemplate messagingTemplate;
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    @Override
    public void sendNoticeToAdmin(MessageDTO message) {
        Role role = roleRepository.findRoleByName(RoleType.ROLE_ADMIN.toString());
        if (role != null && role.getUsers() != null) {
            role.getUsers().forEach(u -> {
                messagingTemplate.convertAndSendToUser(u.getUsername(), "/chat/listen", message);
            });
        }
    }
}
