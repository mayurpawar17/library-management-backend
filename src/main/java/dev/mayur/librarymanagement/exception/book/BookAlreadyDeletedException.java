package dev.mayur.librarymanagement.exception.book;

import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

public class BookAlreadyDeletedException extends BaseApiException {

    public BookAlreadyDeletedException(Long id) {
        super("Book already deleted with id: " + id, HttpStatus.NOT_FOUND, "BOOK_ALREADY_DELETED");
    }
}
