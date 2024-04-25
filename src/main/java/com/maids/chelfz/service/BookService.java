package com.maids.chelfz.service;

import com.maids.chelfz.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
     List<Book> getAllBooks();
     Optional<Book> getBookById(Long id);
     Optional<Book> getBookByTitle(String title);
     Book saveBook(Book book);
     void deleteBook(Long id);
}
