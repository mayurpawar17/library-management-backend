package dev.mayur.librarymanagement.core.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean status;  // e.g., "success" or "error"
    private String message; // Human-readable message
    private T data;         // The actual payload (BookResponse, List, etc.)
    private String errorCode;

    // Quick helper for success responses
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data,null);
    }

    // Quick helper for error responses
    public static <T> ApiResponse<T> error(String message,String errorCode) {
        return new ApiResponse<>(false, message, null,errorCode);
    }
}