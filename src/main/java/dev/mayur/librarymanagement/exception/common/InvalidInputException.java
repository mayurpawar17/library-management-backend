package dev.mayur.librarymanagement.exception.common;

import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

// Thrown for null/invalid IDs or bad request params
// Used in: getBookById(), updateBook(), getAllBooks()
public class InvalidInputException extends BaseApiException {

    public InvalidInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_INPUT");
    }
}

