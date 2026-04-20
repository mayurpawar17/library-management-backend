package dev.mayur.librarymanagement.core.mapper;

import dev.mayur.librarymanagement.features.borrow.dto.BorrowResponse;
import dev.mayur.librarymanagement.features.borrow.entity.Borrow;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BorrowMapper {

    BorrowResponse toResponseList(Borrow borrow);

    List<BorrowResponse> toResponseList(List<Borrow> borrow);

}

