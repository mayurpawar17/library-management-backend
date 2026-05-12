package dev.mayur.librarymanagement.exception.handler;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import dev.mayur.librarymanagement.exception.base.BaseApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handle duplicate book (your case)
    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(BaseApiException ex, HttpServletRequest request) {
        return  ResponseEntity.status(ex.getHttpStatus()).body(ApiResponse.error(ex.getMessage(), ex.getErrorCode()));
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });

        return errors;
    }

    // Handle DB constraint (backup safety)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDuplicate(DataIntegrityViolationException ex) {
        return Map.of("error", "Duplicate book not allowed");
    }
}
