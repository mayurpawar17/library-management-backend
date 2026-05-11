package dev.mayur.librarymanagement.features.auth.dto;

import dev.mayur.librarymanagement.features.auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Used by POST /auth/register
// @Data generates getters/setters so Spring can deserialize JSON into this object
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    // Never accept passwords shorter than 8 chars — basic security hygiene
    private String password;

    // Role is optional — defaults to MEMBER if not provided
    // Admin accounts should be created carefully, not by open registration
    private Role role = Role.ROLE_MEMBER;
}
