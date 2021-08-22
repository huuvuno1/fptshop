package nguyenhuuvu.configuration;

import lombok.RequiredArgsConstructor;
import nguyenhuuvu.filter.JwtAuthenticationEntryPoint;
import nguyenhuuvu.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    final JwtRequestFilter jwtRequestFilter;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/",
                        "/js/**",
                        "/js_web/**",
                        "/images/**",
                        "/fonts/**",
                        "/scss/**",
                        "/vendors/**",
                        "/js/**",
                        "/css/**",
                        "/css_web/**",
                        "/img/**",
                        "/dien-thoai/**",
                        "/laptop/**",
                        "/dong-ho/**",
                        "/admin/login/**",
                        "/authentication"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/admin/users/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/admin/products/**").hasAnyRole("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable();

    }
}
