package com.maids.chelfz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Borrow;
import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.exception.ConflictException;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.repository.BorrowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BorrowServiceImplTest {

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private PatronServiceImpl patronService;

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private BorrowServiceImpl borrowService;

    private Borrow borrow;
    private Patron patron;
    private Book book;

    @BeforeEach
    void setUp() {
        patron = new Patron(1L, "Mohamed Sharaf", "Mohamed.Sharaf@example.com", "01098988481", "123 Street, City");
        book = new Book(1L, "Effective Java", "Mahmoud Sharaf", 2008, "978-0134685991", false);
        borrow = new Borrow(1L, book, patron, LocalDate.now(), null);
    }

    @Test
    void testBorrowRecords() {
        when(borrowRepository.findAll()).thenReturn(Collections.singletonList(borrow));

        List<Borrow> borrowRecords = borrowService.borrowRecords();

        assertNotNull(borrowRecords);
        assertEquals(1, borrowRecords.size());
        verify(borrowRepository, times(1)).findAll();
    }

    @Test
    void testSaveBorrowRecord() {
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        borrowService.saveBorrowRecord(borrow);

        verify(borrowRepository, times(1)).save(borrow);
    }

    @Test
    void testBorrowsByPatronId() {
        when(borrowRepository.findByPatronId(1L)).thenReturn(Collections.singletonList(borrow));

        List<Borrow> borrows = borrowService.borrowsByPatronId(1L);

        assertNotNull(borrows);
        assertEquals(1, borrows.size());
        verify(borrowRepository, times(1)).findByPatronId(1L);
    }

    @Test
    void testFindBorrowRecordByPatronAndBook() {
        when(borrowRepository.findByPatronIdAndBookId(1L, 1L)).thenReturn(Optional.of(borrow));

        Optional<Borrow> borrowRecord = borrowService.findBorrowRecordByPatronAndBook(1L, 1L);

        assertTrue(borrowRecord.isPresent());
        assertEquals(borrow, borrowRecord.get());
        verify(borrowRepository, times(1)).findByPatronIdAndBookId(1L, 1L);
    }

    @Test
    void testBorrowBook_Success() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        Borrow result = borrowService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(patron, result.getPatron());
        assertEquals(book, result.getBook());
        verify(patronService, times(1)).getPatronById(1L);
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).saveBook(book);
        verify(borrowRepository, times(1)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBook_PatronNotFound() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> borrowService.borrowBook(1L, 1L));

        verify(patronService, times(1)).getPatronById(1L);
        verify(bookService, times(0)).getBookById(anyLong());
        verify(bookService, times(0)).saveBook(any(Book.class));
        verify(borrowRepository, times(0)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> borrowService.borrowBook(1L, 1L));

        verify(patronService, times(1)).getPatronById(1L);
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(0)).saveBook(any(Book.class));
        verify(borrowRepository, times(0)).save(any(Borrow.class));
    }

    @Test
    void testBorrowBook_BookAlreadyBorrowed() {
        book.setIsBorrowed(true);
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        assertThrows(ConflictException.class, () -> borrowService.borrowBook(1L, 1L));

        verify(patronService, times(1)).getPatronById(1L);
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(0)).saveBook(any(Book.class));
        verify(borrowRepository, times(0)).save(any(Borrow.class));
    }

    @Test
    void testReturnBook_Success() {
        when(borrowRepository.findByPatronIdAndBookId(1L, 1L)).thenReturn(Optional.of(borrow));

        borrowService.returnBook(1L, 1L);

        assertFalse(book.getIsBorrowed());
        verify(borrowRepository, times(1)).findByPatronIdAndBookId(1L, 1L);
        verify(borrowRepository, times(1)).delete(borrow);
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void testReturnBook_BorrowRecordNotFound() {
        when(borrowRepository.findByPatronIdAndBookId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> borrowService.returnBook(1L, 1L));

        verify(borrowRepository, times(1)).findByPatronIdAndBookId(1L, 1L);
        verify(borrowRepository, times(0)).delete(any(Borrow.class));
        verify(bookService, times(0)).saveBook(any(Book.class));
    }
}
