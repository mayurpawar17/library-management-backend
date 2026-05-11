package dev.mayur.librarymanagement.exception.book;

import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

public class BookDeletionException extends BaseApiException {

    public BookDeletionException(Long id) {
        super("Failed to delete book with id: " + id, HttpStatus.NOT_FOUND, "BOOK_NOT_DELETED");
    }
}