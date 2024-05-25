package com.maids.chelfz.controller;


import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Borrow;
import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.exception.ConflictException;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.service.impl.BookServiceImpl;
import com.maids.chelfz.service.impl.BorrowServiceImpl;
import com.maids.chelfz.service.impl.PatronServiceImpl;
import com.maids.chelfz.util.response.ApiResponse;
import com.maids.chelfz.util.response.ApiResponseManager;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") //borrow

public class BorrowController {


    private final BorrowServiceImpl borrowServiceImpl;
    private final ApiResponseManager<Void> defaultApiResponseManager;
    private final ApiResponseManager<Borrow> borrowApiResponseManager;

    public BorrowController(BorrowServiceImpl borrowServiceImpl,
                            BookServiceImpl bookServiceImpl,
                            PatronServiceImpl patronServiceImpl,
                            ApiResponseManager<Void> defualtApiResponseManager,
                            ApiResponseManager<Borrow> borrowApiResponseManager) {
        this.borrowServiceImpl = borrowServiceImpl;
        this.defaultApiResponseManager = defualtApiResponseManager;
        this.borrowApiResponseManager = borrowApiResponseManager;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<Borrow>> borrow(@PathVariable Long bookId, @PathVariable Long patronId) {
        Borrow borrowRecord = borrowServiceImpl.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.OK)
                .body( borrowApiResponseManager.successMassageData("Borrowed successfully", borrowRecord));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<Void>> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowServiceImpl.returnBook(bookId, patronId);
        return ResponseEntity.ok(defaultApiResponseManager.successMassage( "Book returned successfully for Patron: " + patronId + " and book: " + bookId));
    }

    @GetMapping("/getBorrowRecords")
    public ResponseEntity<List<Borrow>> getAllBorrowRecords() {
        return ResponseEntity.ok(borrowServiceImpl.borrowRecords());
    }
    @GetMapping("/borrow/{patronId}")
    public ResponseEntity<List<Borrow>> findByPatronId(@Valid @PathVariable Long patronId) {
        return ResponseEntity.ok(borrowServiceImpl.borrowsByPatronId(patronId));
    }


}