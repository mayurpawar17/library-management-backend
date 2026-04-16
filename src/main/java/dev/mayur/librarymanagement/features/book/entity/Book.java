package dev.mayur.librarymanagement.features.book.entity;

import dev.mayur.librarymanagement.features.category.entity.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "author"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    private String isbn;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    //AUDIT FIELDS (VERY IMPORTANT IN PRODUCTION)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // This is the Foreign Key
    private Category category;

}
