package dev.mayur.librarymanagement.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// All your custom exceptions extend this
@Getter
public abstract class BaseApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;

    protected BaseApiException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
