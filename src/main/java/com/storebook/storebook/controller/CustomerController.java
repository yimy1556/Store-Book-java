package com.storebook.storebook.controller;

import com.storebook.storebook.entity.Customer;
import com.storebook.storebook.entity.Purchase;
import com.storebook.storebook.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    /*
     * Permite registrarse como usuario
     */
    @PostMapping("/customers")
    public ResponseEntity<Map<String, Object>> seveCustomer(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer).customerDTO());
    }

    /*
     * Devuelve un JSON con la información del cliente
     */
    @GetMapping("/customers/{customerId}")
    public  ResponseEntity<Map<String, Object>> customerFindById(@PathVariable long customerId, Authentication authentication){
        Optional<Customer> customerOptional = customerService.findById(customerId);
        boolean customerVal = customerOptional.get().getEmail().equals(authentication.getName());

        if (customerOptional.isPresent() || customerVal ) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(customerOptional.get().customerDTO());
    }

    /*
     * Permite al cliente comprar un nuevo libro
     */
    @GetMapping("/customers/{customerId}/books")
    public  ResponseEntity<List<Map<String, Object>>> customerBooksFindById(@PathVariable long customerId){
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) return ResponseEntity.noContent().build();

        List<Map<String, Object>> bookList = customerOptional.get().getPurchaseSet().stream().map(Purchase::purchaseBookDTO).collect(Collectors.toList());

        return ResponseEntity.ok().body(bookList);
    }

}
