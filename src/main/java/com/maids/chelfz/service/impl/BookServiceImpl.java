package com.maids.chelfz.service.impl;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.repository.BookRepository;
import com.maids.chelfz.service.BookService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}
