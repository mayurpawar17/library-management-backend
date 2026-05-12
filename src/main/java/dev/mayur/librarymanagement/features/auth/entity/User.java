package dev.mayur.librarymanagement.features.auth.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data                        // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor           // Lombok: generates default constructor (required by JPA)
@AllArgsConstructor          // Lombok: generates all-args constructor
@Builder                     // Lombok: enables User.builder().email(...).build() pattern
public class User implements UserDetails {
    // UserDetails is Spring Security's interface — it tells Spring how to
    // authenticate this user (password, roles, account status)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment primary key
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    // unique = true adds a DB constraint — no two users with same email
    private String email;

    @Column(nullable = false)
    private String password;    // stored as BCrypt hash, NEVER plain text

    @Enumerated(EnumType.STRING)
    // EnumType.STRING stores "ROLE_ADMIN" in DB instead of ordinal (0,1)
    // Always use STRING — if enum order changes, ordinal breaks your data
    @Column(nullable = false)
    private Role role;

    // ── UserDetails interface methods ──────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security needs a list of GrantedAuthority objects
        // SimpleGrantedAuthority wraps our role string (e.g. "ROLE_ADMIN")
        // @PreAuthorize("hasRole('ADMIN')") checks for "ROLE_ADMIN" internally
//        return List.of(new SimpleGrantedAuthority(role.name()));
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    public String getUsername() {
        // We use email as the unique username for login
        // Spring Security calls this to identify the user
        return email;
    }

    @Override
    public String getPassword() {
        // Returns the BCrypt-hashed password
        // Spring Security compares this with the raw password from login request
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return false here to lock expired accounts
        // For MVP: always true (accounts never expire)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return false to lock accounts (e.g. after N failed login attempts)
        // For MVP: always true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return false to force password reset
        // For MVP: always true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return false to disable the account entirely (e.g. banned users)
        // For MVP: always true
        return true;
    }
}

