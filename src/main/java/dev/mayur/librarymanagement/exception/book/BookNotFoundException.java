package dev.mayur.librarymanagement.exception.book;


import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

// Thrown when book lookup by ID fails
// Used in: getBookById(), updateBook(), deleteBook(), restoreBook()
public class BookNotFoundException extends BaseApiException {

    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id, HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND");
    }
}



