package nguyenhuuvu.service.impl;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.entity.Role;
import nguyenhuuvu.entity.User;
import nguyenhuuvu.enums.RoleType;
import nguyenhuuvu.repository.RoleRepository;
import nguyenhuuvu.repository.UserRepository;
import nguyenhuuvu.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User createSessionUser(User user) {
        user.setUsername("user" + System.currentTimeMillis());
        Role role = roleRepository.findRoleByName(RoleType.ROLE_USER_SESSION.toString());
        if (role == null) {
            role = new Role();
            role.setName(RoleType.ROLE_USER_SESSION.toString());
            roleRepository.save(role);
        }
        user.setRoles(Stream.of(role).collect(Collectors.toList()));
        return userRepository.save(user);
    }

}
