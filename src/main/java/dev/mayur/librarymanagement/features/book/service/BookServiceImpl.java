package dev.mayur.librarymanagement.features.book.service;


import dev.mayur.librarymanagement.core.mapper.BookMapper;
import dev.mayur.librarymanagement.exception.ResourceNotFoundException;
import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import dev.mayur.librarymanagement.features.book.entity.Book;
import dev.mayur.librarymanagement.features.book.repository.BookRepository;
import dev.mayur.librarymanagement.features.category.entity.Category;
import dev.mayur.librarymanagement.features.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
            throw new RuntimeException("Book with name " + bookRequest.getTitle() + " and author " + bookRequest.getAuthor() + " already exists");
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
            throw new IllegalArgumentException("Invalid Book ID provided.");
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book " + "with id " + id + " not found"));
        return bookMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findByDeletedAtIsNull();
        return bookMapper.toResponseList(books);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Book ID provided.");
        }
        // 1. Find the existing book or throw a "Not Found" exception
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // 2. Update the fields
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setAuthor(bookRequest.getAuthor());
        existingBook.setPrice(bookRequest.getPrice());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id: " + bookRequest.getCategoryId()));
        existingBook.setCategory(category);

        bookRepository.save(existingBook);
        return bookMapper.toResponse(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getDeletedAt() != null) {
            throw new RuntimeException("Book already deleted");
        }

        book.setDeletedAt(LocalDateTime.now());
        bookRepository.save(book);
    }

    public void restoreBook(Long id) {
        Book book = bookRepository.findByIdIncludingDeleted(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getDeletedAt() == null) {
            throw new RuntimeException("Book is not deleted");
        }

        book.setDeletedAt(null);
        bookRepository.save(book);
    }


}