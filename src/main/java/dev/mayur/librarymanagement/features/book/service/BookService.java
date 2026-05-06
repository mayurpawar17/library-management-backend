package dev.mayur.librarymanagement.features.book.service;

import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    BookResponse getBookById(Long id);

    BookResponse createBook(BookRequest request);

    Page<BookResponse> getAllBooks(int page,
                                   int size);

    BookResponse updateBook(Long id, BookRequest request);

    void deleteBook(Long id);

    void restoreBook(Long id);
}