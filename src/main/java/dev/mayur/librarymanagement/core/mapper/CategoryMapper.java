package dev.mayur.librarymanagement.core.mapper;

import dev.mayur.librarymanagement.features.category.dto.CategoryResponse;
import dev.mayur.librarymanagement.features.category.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponse(List<Category> categories);
}
