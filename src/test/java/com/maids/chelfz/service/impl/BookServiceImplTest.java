package com.maids.chelfz.service.impl;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {
        // Mock data
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Book 1", "Author 1", 2021, "ISBN1", false));
        mockBooks.add(new Book(2L, "Book 2", "Author 2", 2022, "ISBN2", true));
        when(bookRepository.findAll()).thenReturn(mockBooks);
        List<Book> returnedBooks = bookService.getAllBooks();
        assertEquals(2, returnedBooks.size());
        assertEquals(mockBooks, returnedBooks);
    }

    @Test
    public void testGetBookById_BookExists() {
        Long bookId = 1L;
        Book mockBook = new Book(bookId, "Book 1", "Author 1", 2021, "ISBN1", false);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        Optional<Book> returnedBook = bookService.getBookById(bookId);
        assertTrue(returnedBook.isPresent());
        assertEquals(mockBook, returnedBook.get());
    }

    @Test
    public void testGetBookById_BookNotFound() {
        Long bookId = 2L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Optional<Book> returnedBook = bookService.getBookById(bookId);
        assertFalse(returnedBook.isPresent());
    }
    @Test
    public void testGetBookByTitle_BookExists() {
        String title = "Book Title";
        Book mockBook = new Book(1L, title, "Author", 2021, "ISBN123", false);
        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(mockBook));
        Optional<Book> returnedBook = bookService.getBookByTitle(title);
        assertTrue(returnedBook.isPresent());
        assertEquals(mockBook, returnedBook.get());
    }

    @Test
    public void testGetBookByTitle_BookNotFound() {
        String title = "Non-existent Title";
        when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());
        Optional<Book> returnedBook = bookService.getBookByTitle(title);
        assertFalse(returnedBook.isPresent());
    }

    @Test
    public void testSaveBook() {
        Book bookToSave = new Book(null, "Title", "Author", 2021, "ISBN123", false);
        Book savedBook = new Book(1L, "Title", "Author", 2021, "ISBN123", false);
        when(bookRepository.save(bookToSave)).thenReturn(savedBook);
        Book returnedBook = bookService.saveBook(bookToSave);
        assertEquals(savedBook, returnedBook);
        verify(bookRepository, times(1)).save(bookToSave);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

}