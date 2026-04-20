package dev.mayur.librarymanagement.features.borrow.controller;

import dev.mayur.librarymanagement.core.dto.ApiResponse;
import dev.mayur.librarymanagement.features.borrow.dto.BorrowRequest;
import dev.mayur.librarymanagement.features.borrow.dto.BorrowResponse;
import dev.mayur.librarymanagement.features.borrow.service.BorrowServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {

    private final BorrowServiceImpl borrowService;

    public BorrowController(BorrowServiceImpl borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BorrowResponse>> borrowBook(@RequestBody BorrowRequest borrowRequest) {

        BorrowResponse data = borrowService.borrowBook(borrowRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book borrowed successfully", data));
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<ApiResponse<BorrowResponse>> returnBook(@PathVariable Long id) {
        BorrowResponse data = borrowService.returnBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Book returned successfully", data));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BorrowResponse>>> getUserBorrows(@PathVariable Long userId) {
        List<BorrowResponse> data=borrowService.getUserBorrows(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("User borrow list",  data));

    }
}
