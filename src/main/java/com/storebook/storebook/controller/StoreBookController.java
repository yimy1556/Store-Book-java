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

    @Autowired
    private  CustomerService customerService;

    /*-------Rutas Pedidas----------*/

    // ruta 1
    @GetMapping("/books")
    public List<Map<String, Object>> storesBookFindAll() {

        return storeBookService.findAll().stream().map(StoreBook::storeBookDTO).collect(Collectors.toList());
    }

    // ruta 2
    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<Map<String, Object>>> storesBookFindByBookId(@PathVariable Long bookId) {
        Optional<Book> optionalBook = bookService.findById(bookId);

        if(optionalBook.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> listStoresBook = storeBookService.findByBookId(bookId).stream().map(StoreBook::storeBookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(listStoresBook);
    }

    // ruta 3
    @GetMapping("/authors")
    public List<Map<String, Object>> authorFindAll() {

        return authorService.findAll().stream().map(Author::authorDTO).collect(Collectors.toList());
    }

    // ruta 4
    @PostMapping("/author")
    public ResponseEntity<Author> authorSave(@RequestBody Author author){
        return ResponseEntity.ok().body(authorService.save(author));
    }

    // ruta 5
    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<Map<String, Object>>> booksFindByAuthorId(@PathVariable Long authorId){
        Optional<Author> authorOptional = authorService.findById(authorId);

        if (authorOptional.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> listBooks = authorOptional.get().getBookSet().stream().map(Book::bookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(listBooks);
    }

    // ruta 6
    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<Map<String, Object>> saveBook(@RequestBody Book book, @PathVariable Long authorId) {
        Optional<Author> author = authorService.findById(authorId);

        if (author.isEmpty()) ResponseEntity.notFound().build();

        book.setAuthorId(author.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book).bookDTO());
    }

    // ruta 7
    @GetMapping("/stores")
    public List<Map<String, Object>> storesFindAll() {

        return storeService.findAll().stream().map(Store::storeDTO).collect(Collectors.toList());
    }

    // ruta 8
    @PostMapping("/stores")
    public ResponseEntity<Map<String, Object>> saveStore(@RequestBody Store store) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(store).storeDTO());
    }

    // ruta 9
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<Map<String, Object>> storeFindById(@PathVariable Long storeId) {
        Optional<Store> store = storeService.findById(storeId);

        if (!store.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(store.get().storeDTO());
    }

    // ruta 10
    @PostMapping("/stores/{storeId}/books/{bookId}/{quantity}")
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

    // ruta 13
    @PostMapping("/customers")
    public ResponseEntity<Map<String, Object>> seveCustomer(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer).customerDTO());
    }

    // ruta 14
    @GetMapping("/customers/{customerId}")
    public  ResponseEntity<Map<String, Object>> customerFindById(@PathVariable long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customerOptional.get().customerDTO());
    }

    // ruta 15
    @GetMapping("/customers/{customerId}/books")
    public  ResponseEntity<List<Map<String, Object>>> customerBooksFindById(@PathVariable long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> bookList = customerOptional.get().getPurchaseSet().stream().map(Purchase::purchaseBookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(bookList);
    }

    /*----------------------------------------------------------------------------------------------*/

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

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Map<String, Object>> bookFindById(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);

        if (!book.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(book.get().bookDTO());
    }

    @RequestMapping(value = "/bookss", method = RequestMethod.GET)
    public List<Map<String, Object>> booksFindAll() {

        return bookService.findAll().stream().map(Book::bookDTO).collect(Collectors.toList());

    }

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

}