package dev.mayur.librarymanagement.features.borrow.repository;

import dev.mayur.librarymanagement.core.enums.BorrowStatus;
import dev.mayur.librarymanagement.features.borrow.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUserId(Long userId);

    Optional<Borrow> findByIdAndStatus(Long id, BorrowStatus status);
}
