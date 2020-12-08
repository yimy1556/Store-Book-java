package com.storebook.storebook.service;

import com.storebook.storebook.entity.Admin;
import com.storebook.storebook.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public List<Author> findAll();

    public Optional<Author> findById(Long id);

    public Author save(Author author);

    public void delateById(Long id);
}
