package com.maids.chelfz.controller;

import com.maids.chelfz.util.response.ApiResponse;
import com.maids.chelfz.util.response.ApiResponseManager;
import com.maids.chelfz.entity.Book;
import com.maids.chelfz.exception.ConflictException;
import com.maids.chelfz.exception.RecordNotFoundException;
import com.maids.chelfz.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
class BookController {

    private final BookServiceImpl bookServiceImpl;
    private final ApiResponseManager<Book> bookApiResponseManager;
    private final ApiResponseManager<List<Book>> bookListApiResponseManager;
    private final ApiResponseManager<Void> defualtApiResponseManager;

    public BookController(BookServiceImpl bookServiceImpl ,
                          ApiResponseManager<Book> bookApiResponseManager,
                          ApiResponseManager<List<Book>> bookListApiResponseManager,
                          ApiResponseManager<Void> defualtApiResponseManager
                          ) {
        this.bookServiceImpl = bookServiceImpl;
        this.bookApiResponseManager = bookApiResponseManager;
        this.bookListApiResponseManager = bookListApiResponseManager;
        this.defualtApiResponseManager = defualtApiResponseManager;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookServiceImpl.getAllBooks();
        return ResponseEntity.ok(bookListApiResponseManager
                .successResponse(books));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {

        return bookServiceImpl.getBookById(id)
                .map(book ->ResponseEntity.status(HttpStatus.OK).body(bookApiResponseManager.successResponse(book)))
                .orElseThrow(() -> new RecordNotFoundException("Book not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody Book book) {
       Optional<Book> existBook = bookServiceImpl.getBookByTitle(book.getTitle());
       if(existBook.isPresent()){
           throw new ConflictException("this book is already exist");
       }

        Book savedBook = bookServiceImpl.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookApiResponseManager.successMassageData("Book Added Successfully",savedBook));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@Valid @PathVariable Long id,@Valid @RequestBody Book updatedBook) {
        Optional<Book> existingBookOptional = bookServiceImpl.getBookById(id);
        if ( existingBookOptional.isEmpty()) {
             throw new RecordNotFoundException("this Book Not Found");
        }
        Book existingBook = existingBookOptional.get();
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setIsbn(updatedBook.getIsbn());

        Book savedBook = bookServiceImpl.saveBook(existingBook);
        return  ResponseEntity.ok(bookApiResponseManager.successMassageData("Book Updated Successfully",savedBook));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        Optional<Book> existingBookOptional = bookServiceImpl.getBookById(id);
        if ( existingBookOptional.isEmpty()) {
            throw new RecordNotFoundException("this Book Not Found");
        }
        bookServiceImpl.deleteBook(id);
        return ResponseEntity.ok(defualtApiResponseManager.successMassage( "Deleted successfully"));
    }



}
