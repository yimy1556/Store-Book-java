package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.Customer;
import com.storebook.storebook.repository.BookRepository;
import com.storebook.storebook.repository.CustomerRepository;
import com.storebook.storebook.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delateById(Long id) {
        customerRepository.deleteById(id);
    }
}