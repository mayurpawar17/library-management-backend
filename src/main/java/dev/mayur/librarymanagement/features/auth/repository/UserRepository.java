package dev.mayur.librarymanagement.features.auth.repository;

import dev.mayur.librarymanagement.features.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA auto-generates the SQL:
    // SELECT * FROM users WHERE email = ? LIMIT 1
    // Optional<> forces the caller to handle the "user not found" case
    Optional<User> findByEmail(String email);

    // Used during registration to prevent duplicate accounts
    boolean existsByEmail(String email);
}
