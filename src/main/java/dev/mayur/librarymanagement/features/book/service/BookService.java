package dev.mayur.librarymanagement.features.book.service;

import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;

public interface BookService {
    BookResponse getBookById(Long id);

    BookResponse createBook(BookRequest request);
}