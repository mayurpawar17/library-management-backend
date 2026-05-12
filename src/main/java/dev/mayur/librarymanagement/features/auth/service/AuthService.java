package dev.mayur.librarymanagement.features.auth.service;


import dev.mayur.librarymanagement.features.auth.dto.AuthResponse;
import dev.mayur.librarymanagement.features.auth.dto.LoginRequest;
import dev.mayur.librarymanagement.features.auth.dto.RegisterRequest;
import dev.mayur.librarymanagement.features.auth.entity.Role;
import dev.mayur.librarymanagement.features.auth.entity.User;
import dev.mayur.librarymanagement.features.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already registered: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                // BCrypt hash — raw password never saved
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.MEMBER)
                .build();

        userRepository.save(user);

        // TODO: replace this with JWT token generation when you add JWT
        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Registered successfully")
                .build();

    }

//    public AuthResponse login(LoginRequest request) {
//
//        try {
//            // authenticate() → loads user by email → BCrypt-compares password
//            // throws BadCredentialsException if either check fails
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(
////                            request.getEmail(),
////                            request.getPassword()
////                    )
////            );
//
//            Authentication authentication =
//                    authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    request.getEmail(),
//                                    request.getPassword()
//                            )
//                    );
//            SecurityContextHolder.getContext()
//                    .setAuthentication(authentication);
//        } catch (BadCredentialsException e) {
//            // Never reveal whether email or password was wrong
//            throw new BadCredentialsException("Invalid email or password");
//        }
//
//        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//
//        // TODO: replace this with JWT token generation when you add JWT
//        return AuthResponse.builder()
//                .email(user.getEmail())
//                .role(user.getRole().name())
//                .message("Login successful")
//                .build();
//    }

public AuthResponse login(
        LoginRequest request,
        HttpServletRequest httpRequest
) {

    try {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        HttpSession session = httpRequest.getSession(true);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

    } catch (BadCredentialsException e) {

        throw new BadCredentialsException(
                "Invalid email or password"
        );
    }

    User user = userRepository
            .findByEmail(request.getEmail())
            .orElseThrow();

    return AuthResponse.builder()
            .email(user.getEmail())
            .role(user.getRole().name())
            .message("Login successful")
            .build();
}
}









