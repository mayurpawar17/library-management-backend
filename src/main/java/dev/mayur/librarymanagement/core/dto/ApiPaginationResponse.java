package dev.mayur.librarymanagement.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"status", "message", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL) // Hides null fields (like pagination on single-object responses)
public class ApiPaginationResponse<T> {
    private String status;
    private String message;
    private T data;
    private Pagination pagination;


    public static <T> ApiPaginationResponse<T> success(String message, T data) {
        return new ApiPaginationResponse<>("success", message, data, null);
    }


    public static <T> ApiPaginationResponse<T> success(String message, T data, Pagination pagination) {
        return new ApiPaginationResponse<>("success", message, data, pagination);
    }


    public static <T> ApiPaginationResponse<T> error(String message) {
        return new ApiPaginationResponse<>("error", message, null, null);
    }
}
