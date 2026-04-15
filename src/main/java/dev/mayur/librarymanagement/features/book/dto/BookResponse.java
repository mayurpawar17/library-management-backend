package dev.mayur.librarymanagement.features.book.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

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
}
