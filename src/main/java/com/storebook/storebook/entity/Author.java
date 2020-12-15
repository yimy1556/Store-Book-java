package com.storebook.storebook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  String firstName;
    private  String lastName;
    private  String nationality;

    @OneToMany(mappedBy = "authorId" , cascade = {CascadeType.ALL})
    private List<Book> bookSet;


    public Map<String, Object> authorDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("firstName", this.getFirstName());
        dto.put("lastName", this.getLastName());
        dto.put("nationality", this.getNationality());
        return dto;
    }
}
