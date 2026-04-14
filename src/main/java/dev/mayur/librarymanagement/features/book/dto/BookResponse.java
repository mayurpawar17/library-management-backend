package dev.mayur.librarymanagement.features.book.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private String description;

    private String categoryName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
