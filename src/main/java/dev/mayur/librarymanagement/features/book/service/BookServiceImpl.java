package dev.mayur.librarymanagement.features.book.service;

import org.springframework.stereotype.Service;

import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import dev.mayur.librarymanagement.features.book.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse getBookById(Long id) {
        return new BookResponse();
        // business logic
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        return new BookResponse();
        // business logic
    }
}