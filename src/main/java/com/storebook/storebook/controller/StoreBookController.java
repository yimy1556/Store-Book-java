package com.storebook.storebook.controller;

import com.storebook.storebook.entity.Book;
import com.storebook.storebook.entity.Store;
import com.storebook.storebook.entity.StoreBook;
import com.storebook.storebook.service.BookService;

import com.storebook.storebook.service.StoreBookService;
import com.storebook.storebook.service.StoreService;
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

    /* Rutas para desarollador book */
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Book> bookDelete(@PathVariable Long bookId){
        Optional<Book> book = bookService.findById(bookId);

        if(!book.isPresent()) return ResponseEntity.noContent().build();

        bookService.delateById(bookId);

        return ResponseEntity.ok().body(book.get());
    }

    @PatchMapping("/book")
    public ResponseEntity<Book>  bookUpdate(@RequestBody Book bookUpdate){
        Optional<Book> optionalBook = bookService.findById(bookUpdate.getId());

        if(!optionalBook.isPresent()) return ResponseEntity.noContent().build();

        BeanUtils.copyProperties(bookUpdate, optionalBook.get());

        return this.saveBook(optionalBook.get());
    }

    /* rutas de desarollador store */
    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<Store> storeDelete(@PathVariable Long storeId){
        Optional<Store> store = storeService.findById(storeId);

        if(!store.isPresent()) return ResponseEntity.noContent().build();

        bookService.delateById(storeId);

        return ResponseEntity.ok().body(store.get());
    }

    @PatchMapping("/store")
    public ResponseEntity<Store>  storeUpdate(@RequestBody Store storeUpdate){
        Optional<Store> optionalStore = storeService.findById(storeUpdate.getId());

        if(!optionalStore.isPresent()) return ResponseEntity.noContent().build();

        BeanUtils.copyProperties(storeUpdate, optionalStore.get());

        return this.saveStore(optionalStore.get());
    }

    /*---------------------------------------*/
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> bookFindById(@PathVariable Long bookId){
        Optional<Book> book = bookService.findById(bookId);

        if(!book.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(book.get());
    }
    @PostMapping("/book")
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Map<String,Object>> booksFindAll(){
        List<Map<String, Object>> hh = bookService.findAll().stream().map(Book::bookDTO).collect(Collectors.toList());
        System.out.println(hh);
        return hh;
    }

    /* rutas del store */

    @GetMapping("/store/{storeId}")
    public ResponseEntity<Store> storeFindById(@PathVariable Long storeId){
        Optional<Store> store = storeService.findById(storeId);

        if (!store.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(store.get());
    }

    @PostMapping("/store")
    public ResponseEntity<Store> saveStore(@RequestBody Store store){
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(store));
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> storesFindAll(){
        return ResponseEntity.ok().body(storeService.findAll());
    }


    /* Rutas De desarollador */

    @PostMapping("/store/{storeId}/books/{bookId}/{quantity}")
    public ResponseEntity<StoreBook> storeBookseve(@PathVariable Long storeId, @PathVariable Long bookId, @PathVariable int quantity){
        Optional<Store> storeOptional = storeService.findById(storeId);
        Optional<Book> bookOptional = bookService.findById(bookId);

        if(storeOptional.isEmpty() && bookOptional.isEmpty())
            return ResponseEntity.noContent().build();

        StoreBook storeBook = new StoreBook();
        storeBook.setStore(storeOptional.get());
        storeBook.setBook(bookOptional.get());
        storeBook.setStock(quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(storeBookService.save(storeBook));
    }

    @DeleteMapping("/store/book/{id}")
    public ResponseEntity<StoreBook> storeBookUpdate(@PathVariable Long id){
        Optional<StoreBook> storeBook = storeBookService.findById(id);

        if(!storeBook.isPresent()) return ResponseEntity.noContent().build();

        storeBookService.delateById(id);

        return ResponseEntity.ok().body(storeBook.get());
    }
}