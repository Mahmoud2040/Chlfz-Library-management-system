package com.maids.chelfz.service.impl;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Borrow;
import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.exception.ConflictException;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.repository.BookRepository;
import com.maids.chelfz.repository.BorrowRepository;
import com.maids.chelfz.repository.PatronRepository;
import com.maids.chelfz.service.BorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements BorrowService {

    BorrowRepository borrowRepository;
    private final PatronServiceImpl patronServiceImpl;
    private final BookServiceImpl bookServiceImpl;

    public BorrowServiceImpl(BorrowRepository borrowRepository, BookServiceImpl bookServiceImpl, PatronServiceImpl patronServiceImpl) {
        this.borrowRepository = borrowRepository;
        this.bookServiceImpl = bookServiceImpl;
        this.patronServiceImpl = patronServiceImpl;
    }


    @Override
    public List<Borrow> borrowRecords() {
        return borrowRepository.findAll();
    }
    @Override
    public void saveBorrowRecord(Borrow borrow) {
        borrowRepository.save(borrow);
    }
    @Override
    public List<Borrow> borrowsByPatronId (Long patronId){
        return borrowRepository.findByPatronId(patronId);
    }
    @Override
    public Optional <Borrow> findBorrowRecordByPatronAndBook (Long patronId , Long bookId){
        return borrowRepository.findByPatronIdAndBookId(patronId,bookId);
    }

    @Override
    @Transactional
    public Borrow borrowBook(Long bookId, Long patronId) {
        Book book = bookServiceImpl.getBookById(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Book not found"));

        //Borrow Exception
        if (book.getIsBorrowed()) {
            throw new ConflictException("This book is already borrowed");
        }

            Patron patron = patronServiceImpl.getPatronById(patronId)
                    .orElseThrow(() -> new RecordNotFoundException("Patron not found"));



            LocalDate todayDate = LocalDate.now();
            book.setIsBorrowed(true);
            bookServiceImpl.saveBook(book);
        return borrowRepository.save(new Borrow(null,book, patron, todayDate,null));
        }



    @Override
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        Borrow borrow = borrowRepository.findByPatronIdAndBookId(bookId, patronId)
                .orElseThrow(() -> new RecordNotFoundException("No borrow record found for Patron: " + patronId + " and book: " + bookId));


        Book book = borrow.getBook();
        book.setIsBorrowed(false);
        bookServiceImpl.saveBook(book);
    }
}

