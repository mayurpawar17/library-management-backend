package dev.mayur.librarymanagement.features.book.controller;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import dev.mayur.librarymanagement.features.book.dto.BookRequest;
import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import dev.mayur.librarymanagement.features.book.service.BookServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/books")

public class BookController {
    private final BookServiceImpl bookService;


    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse data = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Book created successfully", data));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        List<BookResponse> data = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Books retrieved successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
        BookResponse data = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book retrieved successfully", data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookResponse data = bookService.updateBook(id, bookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book updated successfully", data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book Deleted Successfully", null));
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreBook(@PathVariable Long id) {
        bookService.restoreBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book with ID " + id + " restored successfully!", null));
    }
}
