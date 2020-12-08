package com.storebook.storebook.entity;

import com.storebook.storebook.entity.abs.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Admin extends User {
    private long StoreId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

}
