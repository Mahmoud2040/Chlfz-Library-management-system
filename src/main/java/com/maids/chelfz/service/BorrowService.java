package com.maids.chelfz.service;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Borrow;
import com.maids.chelfz.entity.Patron;

import java.util.List;
import java.util.Optional;

public interface BorrowService {
     List<Borrow> borrowRecords();
     void saveBorrowRecord(Borrow borrow);
     List<Borrow> borrowsByPatronId (Long patronId);
     Optional<Borrow> findBorrowRecordByPatronAndBook (Long patronId , Long bookId);

    Borrow borrowBook(Long bookId, Long patronId);
     public void returnBook(Long bookId, Long patronId);
}
