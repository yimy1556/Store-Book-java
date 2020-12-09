package com.storebook.storebook.controller;

import com.storebook.storebook.entity.*;
import com.storebook.storebook.service.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StoreBookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreBookService storeBookService;

    @Autowired
    private  AuthorService authorService;


    /* Rutas para desarollador book */
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Book> bookDelete(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (!book.isPresent()) return ResponseEntity.noContent().build();

        bookService.delateById(bookId);

        return ResponseEntity.ok().body(book.get());
    }

    @PatchMapping("/book")
    public ResponseEntity<Map<String, Object>> bookUpdate(@RequestBody Book bookUpdate) {
        Optional<Book> optionalBook = bookService.findById(bookUpdate.getId());

        if (!optionalBook.isPresent()) return ResponseEntity.noContent().build();

        optionalBook.get().setTitle(bookUpdate.getTitle());
        optionalBook.get().setCategory(bookUpdate.getCategory());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(optionalBook.get()).bookDTO());
    }

    /*---------------------------------------*/
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Map<String, Object>> bookFindById(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (!book.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(book.get().bookDTO());
    }


    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> saveBook2(@RequestBody Book book) {
        authorService.save(book.getAuthorId());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book).bookDTO());
    }


    @PostMapping("/book/{authorId}")
    public ResponseEntity<Map<String, Object>> saveBook(@RequestBody Book book, @PathVariable Long authorId) {
        Optional<Author> author = authorService.findById(authorId);

        if (!author.isPresent()) ResponseEntity.notFound().build();

        book.setAuthorId(author.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book).bookDTO());
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Map<String, Object>> booksFindAll() {

        return bookService.findAll().stream().map(Book::bookDTO).collect(Collectors.toList());

    }

    /*----------------------------------------------------------------------------------------------*/
    /* rutas de desarollador store */
    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<Store> storeDelete(@PathVariable Long storeId) {
        Optional<Store> store = storeService.findById(storeId);

        if (!store.isPresent()) return ResponseEntity.noContent().build();

        bookService.delateById(storeId);

        return ResponseEntity.ok().body(store.get());
    }

    @PatchMapping("/store")
    public ResponseEntity<Map<String, Object>> storeUpdate(@RequestBody Store storeUpdate) {
        Optional<Store> optionalStore = storeService.findById(storeUpdate.getId());

        if (!optionalStore.isPresent()) return ResponseEntity.noContent().build();

        BeanUtils.copyProperties(storeUpdate, optionalStore.get());

        return this.saveStore(optionalStore.get());
    }

    /* rutas del store */

    @GetMapping("/store/{storeId}")
    public ResponseEntity<Map<String, Object>> storeFindById(@PathVariable Long storeId) {
        Optional<Store> store = storeService.findById(storeId);

        if (!store.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(store.get().storeDTO());
    }

    @PostMapping("/store")
    public ResponseEntity<Map<String, Object>> saveStore(@RequestBody Store store) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(store).storeDTO());
    }

    @GetMapping("/stores")
    public List<Map<String, Object>> storesFindAll() {

        return storeService.findAll().stream().map(Store::storeDTO).collect(Collectors.toList());
    }

    /*-------------------------------------------------------------------------------------------------------*/
    /* Rutas De desarollador */

    @PostMapping("/store/{storeId}/books/{bookId}/{quantity}")
    public ResponseEntity<Map<String, Object>> storeBookseve(@PathVariable Long storeId, @PathVariable Long bookId, @PathVariable int quantity) {
        Optional<Store> optionalStore = storeService.findById(storeId);
        Optional<Book> optionalBook = bookService.findById(bookId);

        if (!optionalStore.isPresent() || !optionalBook.isPresent()) return ResponseEntity.noContent().build();

        StoreBook storeBook = new StoreBook();
        storeBook.setStore(optionalStore.get());
        storeBook.setBook(optionalBook.get());
        storeBook.setStock(quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(storeBookService.save(storeBook).storeBookDTO());
    }

    @GetMapping("/storeBook")
    public List<Map<String, Object>> storesBookFindAll() {

        return storeBookService.findAll().stream().map(StoreBook::storeBookDTO).collect(Collectors.toList());
    }


    @GetMapping("/storeBook/{id}")
    public ResponseEntity<Map<String, Object>> storeBookFindById(@PathVariable Long id) {
        Optional<StoreBook> storeBookOptional = storeBookService.findById(id);

        if (!storeBookOptional.isPresent()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(storeBookOptional.get().storeBookDTO());
    }

    @DeleteMapping("/store/book/{id}")
    public ResponseEntity<Map<String, Object>> storeBookUpdate(@PathVariable Long id) {
        Optional<StoreBook> storeBook = storeBookService.findById(id);

        if (!storeBook.isPresent()) return ResponseEntity.noContent().build();

        storeBookService.delateById(id);

        return ResponseEntity.ok().body(storeBook.get().storeBookDTO());
    }

    /*----------------------------------------------------------------------------*/
    /*Rutas de author*/
    @PostMapping("/author")
    public ResponseEntity<Author> authorSave(@RequestBody Author author){
        return ResponseEntity.ok().body(authorService.save(author));
    }

    @GetMapping("/authores")
    public List<Map<String, Object>> authorFindAll() {

        return authorService.findAll().stream().map(Author::authorDTO).collect(Collectors.toList());
    }

}