package nguyenhuuvu.service.impl;

import lombok.AllArgsConstructor;
import nguyenhuuvu.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {
    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // login with email or username
        nguyenhuuvu.entity.User user = userRepository.findUserByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException("Login false: " + s);
        List<GrantedAuthority> granted =  user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, granted);
    }
}

