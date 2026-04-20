package dev.mayur.librarymanagement.features.borrow.service;

import dev.mayur.librarymanagement.features.borrow.dto.BorrowRequest;
import dev.mayur.librarymanagement.features.borrow.dto.BorrowResponse;

import java.util.List;

public interface BorrowService {
    BorrowResponse borrowBook(BorrowRequest borrowRequest);

    BorrowResponse returnBook(Long borrowId);

    List<BorrowResponse> getUserBorrows(Long userId);
}
