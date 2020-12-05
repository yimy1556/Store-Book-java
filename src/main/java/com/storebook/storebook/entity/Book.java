package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String title;
    private String author;
    private String category; // el type de string tiene que ser un class enum

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StoreBook> storeBooks;

    public Map<String, Object> bookDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("title", this.getTitle());
        dto.put("author", this.getAuthor());
        dto.put("category", this.getCategory());
        return dto;
    }
}