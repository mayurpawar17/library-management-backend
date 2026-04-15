package dev.mayur.librarymanagement.features.book.repository;

import dev.mayur.librarymanagement.features.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long>{
    boolean existsByTitleAndAuthor(String title, String author);

    List<Book> findByDeletedAtIsNull();

    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> findByIdIncludingDeleted(@Param("id") Long id);
}
