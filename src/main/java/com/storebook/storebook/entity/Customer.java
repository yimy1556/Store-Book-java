package com.storebook.storebook.entity;

import com.storebook.storebook.entity.abs.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Customer extends User {
    
    @OneToMany(mappedBy = "customer" , cascade = {CascadeType.ALL})
    private Set<Purchase> purchaseSet ;

    private void addPurchase(Purchase purchase){
        this.getPurchaseSet().add(purchase);
    }


}
