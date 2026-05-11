package dev.mayur.librarymanagement.features.book.service;


import dev.mayur.librarymanagement.core.mapper.BookMapper;
import dev.mayur.librarymanagement.exception.book.BookAlreadyDeletedException;
import dev.mayur.librarymanagement.exception.book.BookDeletionException;
import dev.mayur.librarymanagement.exception.book.BookNotFoundException;
import dev.mayur.librarymanagement.exception.book.DuplicateBookException;
import dev.mayur.librarymanagement.exception.category.CategoryNotFoundException;
import dev.mayur.librarymanagement.exception.common.InvalidInputException;
import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import dev.mayur.librarymanagement.features.book.entity.Book;
import dev.mayur.librarymanagement.features.book.repository.BookRepository;
import dev.mayur.librarymanagement.features.category.entity.Category;
import dev.mayur.librarymanagement.features.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        // 1. Find the category first
        Category category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        boolean exists = bookRepository.existsByTitleAndAuthor(bookRequest.getTitle(), bookRequest.getAuthor());

        if (exists) {
            throw new DuplicateBookException(bookRequest.getTitle(), bookRequest.getAuthor());
        }

        // Create a new Entity instance
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setIsbn(bookRequest.getIsbn());
        book.setDeletedAt(null);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setCategory(category); // Set the entity relationship
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Override
    public BookResponse getBookById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("Book ID must be a positive number, received: " + id);
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toResponse(book);
    }

    @Override
    public Page<BookResponse> getAllBooks(int page, int size) {

        // InvalidInputException → 400
        // Guards invalid pagination params before DB query
        if (page < 0) {
            throw new InvalidInputException("Page index must not be negative, received: " + page);
        }
        if (size <= 0 || size > 100) {
            throw new InvalidInputException("Page size must be between 1 and 100, received: " + size);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> bookPage;
        bookPage = bookRepository.findByDeletedAtIsNull(pageable);
//        return bookMapper.toResponseList(bookPage);
        return bookPage.map(bookMapper::toResponse);
    }


    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        // InvalidInputException → 400
        if (id == null || id <= 0) {
            throw new InvalidInputException("Book ID must be a positive number, received: " + id);
        }
        // 1. Find the existing book or throw a "Not Found" exception
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        // 2. Update the fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setPrice(bookRequest.getPrice());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(bookRequest.getCategoryId()));
        existingBook.setCategory(category);

        bookRepository.save(existingBook);
        return bookMapper.toResponse(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        if (book.getDeletedAt() != null) {
            throw new BookAlreadyDeletedException(id);
        }

        book.setDeletedAt(LocalDateTime.now());
        bookRepository.save(book);
    }

    public void restoreBook(Long id) {
        Book book = bookRepository.findByIdIncludingDeleted(id).orElseThrow(() -> new BookNotFoundException(id));

        if (book.getDeletedAt() == null) {
            throw new BookDeletionException(id);
        }

        book.setDeletedAt(null);
        bookRepository.save(book);
    }


}