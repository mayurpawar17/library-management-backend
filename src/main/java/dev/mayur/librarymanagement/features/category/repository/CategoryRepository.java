package dev.mayur.librarymanagement.features.category.repository;

import dev.mayur.librarymanagement.features.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}