package com.storebook.storebook.service;

import com.storebook.storebook.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<Customer> findAll();

    public Optional<Customer> findById(Long id);

    public Customer save(Customer customer);

    public void delateById(Long id);

}
