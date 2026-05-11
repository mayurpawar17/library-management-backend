package dev.mayur.librarymanagement.exception.category;

import dev.mayur.librarymanagement.exception.base.BaseApiException;
import org.springframework.http.HttpStatus;

// Thrown when category lookup by ID fails
// Used in: createBook(), updateBook(), createCategory(), updateCategory()
public class CategoryNotFoundException extends BaseApiException {

    public CategoryNotFoundException(Long id) {
        super("Category not found with id: " + id, HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND");
    }
}
