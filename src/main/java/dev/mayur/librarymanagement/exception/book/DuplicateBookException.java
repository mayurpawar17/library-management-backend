package dev.mayur.librarymanagement.exception.book;

import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

// Thrown when a book with same title + author already exists
// Used in: createBook()
public class DuplicateBookException extends BaseApiException {

    public DuplicateBookException(String title, String author) {
        super("Book with title '" + title + "' by author '" + author + "' already exists",
                HttpStatus.CONFLICT, "DUPLICATE_BOOK");
    }
}

