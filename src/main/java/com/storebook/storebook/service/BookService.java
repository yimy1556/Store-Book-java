package com.storebook.storebook.service;

import com.storebook.storebook.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public List<Book> findAll();

    public Optional<Book> findById(Long id);

    public Book save(Book book);

    public void delateById(Long id);

}
