package dev.mayur.librarymanagement.features.book.dto;

import dev.mayur.librarymanagement.features.category.dto.CategoryResponse;
import dev.mayur.librarymanagement.features.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String isbn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    CategoryResponse category;
}
