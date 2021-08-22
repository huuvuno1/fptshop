package nguyenhuuvu.service.impl;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.entity.User;
import nguyenhuuvu.repository.UserRepository;
import nguyenhuuvu.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
