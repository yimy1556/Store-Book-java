package com.storebook.storebook.entity;

import com.storebook.storebook.entity.abs.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Customer extends User {
    
    @OneToMany(mappedBy = "customer" , cascade = {CascadeType.ALL})
    private List<Purchase> purchaseSet ;

    public Map<String, Object> customerDTO(){
        Map<String,Object> dto = new HashMap<>();
        dto.put("id", this.getId());
        dto.put("firstName", this.getFirstName());
        dto.put("lastName", this.getLastName());
        dto.put("email", this.getEmail());
        dto.put("password", this.getPassword());
        return dto;
    }

}
