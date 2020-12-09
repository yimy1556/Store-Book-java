package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author authorId;
    private String category; // el type de string tiene que ser un class enum

    @OneToMany(mappedBy = "book" , cascade = {CascadeType.ALL})
    private Set<StoreBook> storeBooks;

    public Map<String, Object> bookDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("title", this.getTitle());
        dto.put("author", this.getAuthorId().authorDTO());
        dto.put("category", this.getCategory());
        return dto;
    }
}
