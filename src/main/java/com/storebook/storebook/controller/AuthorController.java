package com.storebook.storebook.controller;

import com.storebook.storebook.entity.Author;
import com.storebook.storebook.entity.Book;
import com.storebook.storebook.service.AuthorService;
import com.storebook.storebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    /*
     * Devuelve un JSON con todos los autores de la BBDD.
     */
    @GetMapping()
    public List<Map<String, Object>> authorFindAll() {
        return authorService.findAll().stream().map(Author::authorDTO).collect(Collectors.toList());
    }

    /*
     * Permite guardar un autor en la BBDD.
     */
    @PostMapping()
    public ResponseEntity<Author> authorSave(@RequestBody Author author){
        return ResponseEntity.ok().body(authorService.save(author));
    }

    /*
     * Devuelve un JSON con los libros del autor.
     */
    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Map<String, Object>>> booksFindByAuthorId(@PathVariable Long authorId){
        Optional<Author> authorOptional = authorService.findById(authorId);

        if (authorOptional.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> listBooks = authorOptional.get().getBookSet().stream().map(Book::bookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(listBooks);
    }

    /*
     * Permite cargar un nuevo libro en la BBDD y relacionarlo con su autor.
     */
    @PostMapping("/{authorId}/books")
    public ResponseEntity<Map<String, Object>> saveBook(@RequestBody Book book, @PathVariable Long authorId) {
        Optional<Author> author = authorService.findById(authorId);

        if (author.isEmpty()) ResponseEntity.notFound().build();

        book.setAuthorId(author.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book).bookDTO());
    }

}
