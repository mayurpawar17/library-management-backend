package dev.mayur.librarymanagement.features.book.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;


    private String isbn;

    Long categoryId;

//    @NotNull(message = "Stock is required")
//    @Min(value = 0, message = "Stock cannot be negative")
//    private Integer stock;
//
//    private String description;
//
//    private Long categoryId; // if you have category module
    
}
