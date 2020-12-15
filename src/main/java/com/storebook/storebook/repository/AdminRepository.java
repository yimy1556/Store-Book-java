package com.storebook.storebook.repository;

import com.storebook.storebook.entity.Admin;
import com.storebook.storebook.entity.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT a FROM Admin a WHERE a.email = ?1")
    Admin findByEmail(String email);

}
