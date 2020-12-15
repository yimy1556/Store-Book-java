package com.storebook.storebook.repository;

import com.storebook.storebook.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query(value = "SELECT c FROM Customer c WHERE c.email = ?1")
    Customer findByEmail(String email);

}
