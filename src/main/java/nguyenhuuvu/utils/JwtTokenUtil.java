package nguyenhuuvu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import nguyenhuuvu.entity.Role;
import nguyenhuuvu.entity.User;
import nguyenhuuvu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${nguyenhuuvu.jwt.secret}")
    private String secret;

    @Value("${nguyenhuuvu.jwt.auth}")
    private String auth_key;

    @Value("${nguyenhuuvu.jwt.time-expried}")
    private Integer time_expried;

    final UserRepository userRepository;

    public String generateToken(String username) {
        User user = userRepository.findUserByUsername(username);
        String authorities = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        return doGenerateToken(username, authorities);
    }

    private String doGenerateToken(String subject, String grant) {
        return Jwts.builder().setSubject(subject).setIssuedAt(new Date())
                    .claim(auth_key, grant)
                    .setExpiration(new Date(System.currentTimeMillis() + time_expried))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                    .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        final Date dateInToken = getExpirationDateFromToken(token);
        return dateInToken.before(new Date());
    }
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(auth_key).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }
    public String generateToken(Authentication userDetails) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return doGenerateToken(userDetails.getName(), authorities);
    }
}
