package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.Admin;
import com.storebook.storebook.entity.Customer;
import com.storebook.storebook.repository.AdminRepository;
import com.storebook.storebook.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AdminServiceImp implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Admin> findAll() { return adminRepository.findAll(); }

    @Override
    public Optional<Admin> findById(Long id) { return adminRepository.findById(id); }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void delateById(Long id) { adminRepository.deleteById(id); }
}
