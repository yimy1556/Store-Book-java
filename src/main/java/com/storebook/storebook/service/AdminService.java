package com.storebook.storebook.service;

import com.storebook.storebook.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<Admin> findAll();

    public Optional<Admin> findById(Long id);

    public Admin save(Admin admin);

    public void delateById(Long id);

}
