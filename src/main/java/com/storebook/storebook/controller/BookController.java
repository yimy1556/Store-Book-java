package com.storebook.storebook.controller;

import com.storebook.storebook.entity.Book;
import com.storebook.storebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /*
     * Devuelve un JSON con los datos del libro eliminado indicada por su ID.
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> bookDelete(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (book.isPresent()) return ResponseEntity.noContent().build();

        bookService.delateById(bookId);

        return ResponseEntity.ok().body(book.get());
    }

    /*
     * Devuelve un JSON con los datos del libro modificado indicada por su ID.
     */
    @PatchMapping()
    public ResponseEntity<Map<String, Object>> bookUpdate(@RequestBody Book bookUpdate) {
        Optional<Book> optionalBook = bookService.findById(bookUpdate.getId());

        if (!optionalBook.isPresent()) return ResponseEntity.noContent().build();

        optionalBook.get().setTitle(bookUpdate.getTitle());
        optionalBook.get().setCategory(bookUpdate.getCategory());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(optionalBook.get()).bookDTO());
    }

    /*
     * Devuelve un JSON con los datos de la libro indicada por su ID.
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<Map<String, Object>> bookFindById(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (!book.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(book.get().bookDTO());
    }

    /*
     * Devuelve un JSON con los datos de los libros que estan en la BBDD.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Map<String, Object>> booksFindAll(Authentication authentication) {
        System.out.printf(String.valueOf(authentication.getAuthorities()));


        return bookService.findAll().stream().map(Book::bookDTO).collect(Collectors.toList());

    }

}
