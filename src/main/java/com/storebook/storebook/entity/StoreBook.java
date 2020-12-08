package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


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

    public Map<String,Object> storeBookDTO(){
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", this.getId());
        dto.put("book", this.getBook().bookDTO());
        dto.put("store", this.getStore().storeDTO());
        dto.put("stock", this.getStock());
        return dto;
    }

}
