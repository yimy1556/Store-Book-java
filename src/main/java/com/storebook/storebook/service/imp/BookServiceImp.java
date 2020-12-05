package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.Book;
import com.storebook.storebook.repository.BookRepository;
import com.storebook.storebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository bookRepositoy;

    @Override
    public List<Book> findAll() { return bookRepositoy.findAll(); }

    @Override
    public Optional<Book> findById(Long id) { return bookRepositoy.findById(id); }

    @Override
    public Book save(Book book) { return bookRepositoy.save(book); }

    @Override
    public void delateById(Long id) { bookRepositoy.deleteById(id); }

}
