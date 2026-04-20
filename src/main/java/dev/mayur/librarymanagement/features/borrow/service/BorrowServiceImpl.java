package dev.mayur.librarymanagement.features.borrow.service;

import dev.mayur.librarymanagement.core.enums.BorrowStatus;
import dev.mayur.librarymanagement.core.mapper.BorrowMapper;
import dev.mayur.librarymanagement.features.book.entity.Book;
import dev.mayur.librarymanagement.features.book.repository.BookRepository;
import dev.mayur.librarymanagement.features.borrow.dto.BorrowRequest;
import dev.mayur.librarymanagement.features.borrow.dto.BorrowResponse;
import dev.mayur.librarymanagement.features.borrow.entity.Borrow;
import dev.mayur.librarymanagement.features.borrow.repository.BorrowRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final BorrowMapper borrowMapper;

    public BorrowServiceImpl(BorrowRepository borrowRepository, BookRepository bookRepository, BorrowMapper borrowMapper) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.borrowMapper = borrowMapper;
    }


    @Transactional
    @Override
    public BorrowResponse borrowBook(BorrowRequest borrowRequest) {
        Book book = bookRepository.findById(borrowRequest.getBookId()).orElseThrow(() -> new RuntimeException("Book not " + "found"));
        Borrow borrow = new Borrow();
        borrow.setUserId(borrowRequest.getUserId());
        borrow.setBookId(borrowRequest.getBookId());
        borrow.setBorrowDate(LocalDateTime.now());
        borrow.setDueDate(LocalDateTime.now().plusDays(7));
        borrow.setStatus(BorrowStatus.BORROWED);
        borrowRepository.save(borrow);

        return borrowMapper.toResponseList(borrow);
    }

    @Override
    public BorrowResponse returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId).orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (borrow.getStatus() == BorrowStatus.RETURNED) {
            throw new RuntimeException("Book already returned");
        }

        Book book = bookRepository.findById(borrow.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));

        // update borrow
        borrow.setReturnDate(LocalDateTime.now());

        if (LocalDateTime.now().isAfter(borrow.getDueDate())) {
            borrow.setStatus(BorrowStatus.OVERDUE);
        } else {
            borrow.setStatus(BorrowStatus.RETURNED);
        }
        borrowRepository.save(borrow);
        bookRepository.save(book);


        // increase stock
//        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return borrowMapper.toResponseList(borrow);
    }

    @Override
    public List<BorrowResponse> getUserBorrows(Long userId) {
        List<Borrow> data = borrowRepository.findByUserId(userId);
        return borrowMapper.toResponseList(data);
    }
}
