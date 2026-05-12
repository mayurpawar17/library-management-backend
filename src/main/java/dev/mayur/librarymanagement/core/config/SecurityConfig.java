package dev.mayur.librarymanagement.core.config;

import dev.mayur.librarymanagement.exception.auth.CustomAuthEntryPoint;
import dev.mayur.librarymanagement.features.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity      // activates Spring Security web support
@EnableMethodSecurity   // enables @PreAuthorize on controller methods
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    // BCrypt password hashing — never store plain text passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // Tells Spring how to load a user by username (email in our case)
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    // Wires together UserDetailsService + PasswordEncoder for login
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
//        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Needed in AuthService to trigger programmatic authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF disabled — when you add JWT later it won't use cookies so CSRF is irrelevant
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // ── Auth endpoints — public ──────────────────────────────
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // ── Book endpoints ───────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/**").authenticated().requestMatchers(HttpMethod.POST, "/api/v1/books/**").hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasRole("ADMIN").requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasRole("ADMIN")

                        // ── Member endpoints ─────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/api/v1/members").hasRole("ADMIN").requestMatchers(HttpMethod.GET, "/api/v1/members/**").authenticated().requestMatchers(HttpMethod.POST, "/api/v1/members/**").hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/api/v1/members/**").authenticated().requestMatchers(HttpMethod.DELETE, "/api/members/**").hasRole("ADMIN")

                        // ── Borrow endpoints ─────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/api/v1/borrow").hasRole("ADMIN").requestMatchers(HttpMethod.GET, "/api/v1/borrow/**").authenticated().requestMatchers(HttpMethod.POST, "/api/v1/borrow/**").authenticated().requestMatchers(HttpMethod.PUT, "/api/v1/borrow/**").authenticated()

                        // anything else requires login
                        .anyRequest().authenticated())

                .authenticationProvider(authenticationProvider())

                // ── Temporary basic auth for testing ────────────────────────
                // Remove this when you add JWT — JWT filter replaces this
//                .httpBasic(org.springframework.security.config.Customizer.withDefaults());
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}


