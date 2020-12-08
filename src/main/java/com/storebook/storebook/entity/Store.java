package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "storeId" , cascade = {CascadeType.ALL})
    private Set<Admin> adminSet ;

    @OneToMany(mappedBy = "store", cascade = {CascadeType.ALL})
    private Set<StoreBook> storeBooks;

    public Map<String,Object> storeDTO(){
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", this.getId());
        dto.put("name", this.getName());
        dto.put("address", this.getAddress());
        return dto;
    }
}
