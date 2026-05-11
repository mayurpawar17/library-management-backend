package dev.mayur.librarymanagement.features.auth.dto;

import lombok.Builder;
import lombok.Data;

// Returned after successful login or register
@Data
@Builder
public class AuthResponse {

    private String email;
    private String role;
    private String message;

    // These are null now — will be populated when you add JWT
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
