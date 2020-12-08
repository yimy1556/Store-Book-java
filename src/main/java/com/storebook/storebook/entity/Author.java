package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public abstract class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  String firstName;
    private  String lastName;
    private  String nationality;

    @OneToMany(mappedBy = "authorId" , cascade = {CascadeType.ALL})
    private Set<Book> bookSet;

}
