package dev.mayur.librarymanagement.exception;

import dev.mayur.librarymanagement.core.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
        // return Map.of("error", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
        // Return 404 Not Found status with your custom body
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
