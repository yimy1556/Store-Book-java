package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class StoreBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    private int stock;

}
