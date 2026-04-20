package dev.mayur.librarymanagement.core.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;  // e.g., "success" or "error"
    private String message; // Human-readable message
    private T data;         // The actual payload (BookResponse, List, etc.)

    // Quick helper for success responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    // Quick helper for error responses
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", message, null);
    }
}