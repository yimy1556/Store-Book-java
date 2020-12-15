package com.storebook.storebook.controller;

import com.storebook.storebook.entity.*;
import com.storebook.storebook.service.*;

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

    /*
     * Devuelve un JSON con todos los libros de la base de datos,
     * especificando librerías disponibles y stock
     */
    @GetMapping("/books")
    public List<Map<String, Object>> storesBookFindAll() {

        return storeBookService.findAll().stream().map(StoreBook::storeBookDTO).collect(Collectors.toList());
    }

    /*
     * Devuelve un JSON con los datos del libro indicado por su ID,
     * especificando librerías disponibles y stock
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<Map<String, Object>>> storesBookFindByBookId(@PathVariable Long bookId) {
        Optional<Book> optionalBook = bookService.findById(bookId);

        if(optionalBook.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> listStoresBook = storeBookService.findByBookId(bookId).stream().map(StoreBook::storeBookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(listStoresBook);
    }

    /*
     * Registra un nuevo libro en la librería.
     */
    @PostMapping("/stores/{storeId}/books/{bookId}/{quantity}")
    public ResponseEntity<Map<String, Object>> storeBookseve(@PathVariable Long storeId, @PathVariable Long bookId, @PathVariable int quantity) {
        Optional<Store> optionalStore = storeService.findById(storeId);
        Optional<Book> optionalBook = bookService.findById(bookId);

        if (optionalStore.isEmpty() || optionalBook.isEmpty()) return ResponseEntity.noContent().build();

        StoreBook storeBook = new StoreBook();
        storeBook.setStore(optionalStore.get());
        storeBook.setBook(optionalBook.get());
        storeBook.setStock(quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(storeBookService.save(storeBook).storeBookDTO());
    }

    @DeleteMapping("/store/book/{id}")
    public ResponseEntity<Map<String, Object>> storeBookUpdate(@PathVariable Long id) {
        Optional<StoreBook> storeBook = storeBookService.findById(id);

        if (storeBook.isEmpty()) return ResponseEntity.noContent().build();

        storeBookService.delateById(id);

        return ResponseEntity.ok().body(storeBook.get().storeBookDTO());
    }


    @GetMapping("/storeBook/{id}")
    public ResponseEntity<Map<String, Object>> storeBookFindById(@PathVariable Long id) {
        Optional<StoreBook> storeBookOptional = storeBookService.findById(id);

        if (storeBookOptional.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(storeBookOptional.get().storeBookDTO());
    }
}