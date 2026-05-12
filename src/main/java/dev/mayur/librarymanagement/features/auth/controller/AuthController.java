package dev.mayur.librarymanagement.features.auth.controller;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import dev.mayur.librarymanagement.features.auth.dto.AuthResponse;
import dev.mayur.librarymanagement.features.auth.dto.LoginRequest;
import dev.mayur.librarymanagement.features.auth.dto.RegisterRequest;
import dev.mayur.librarymanagement.features.auth.dto.UserProfileResponse;
import dev.mayur.librarymanagement.features.auth.entity.User;
import dev.mayur.librarymanagement.features.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
//        return ResponseEntity.ok(authService.login(request));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {

        return ResponseEntity.ok(
                authService.login(request, httpRequest)
        );
    }

//    @GetMapping("/me")
//    public Object me(Authentication authentication) {
//        return authentication.getAuthorities();
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        UserProfileResponse response =
                UserProfileResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile retrieved successfully",
                        response
                )
        );
    }

    // TODO: add /refresh endpoint when JWT is wired in
}
