package dev.mayur.librarymanagement.core.mapper;


import dev.mayur.librarymanagement.features.book.dto.BookResponse;
import dev.mayur.librarymanagement.features.book.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {
    // Maps a single Book
    BookResponse toResponse(Book book);

    // Maps a List of Books (MapStruct implements the loop for you!)
    List<BookResponse> toResponseList(List<Book> books);
}
