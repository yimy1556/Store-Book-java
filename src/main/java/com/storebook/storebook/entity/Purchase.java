package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "storeBookId")
    private StoreBook storeBook;



    public Map<String, Object> purchaseDTO(){
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", this.getId());
        dto.put("storeBook", this.getStoreBook().getStore().storeDTO());
        dto.put("book", this.getStoreBook().getBook().bookDTO());
        return dto;
    }

    public Map<String, Object> purchaseBookDTO(){
        Map<String,Object> dto = new HashMap<>();
        dto.put("book", this.getStoreBook().getBook().bookDTO());
        return dto;
    }
}
