package nguyenhuuvu.service;

import nguyenhuuvu.entity.User;

public interface UserService {
    User findUserByUsername(String username);
}
