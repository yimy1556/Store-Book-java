package com.storebook.storebook.service;

import com.storebook.storebook.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    public List<Store> findAll();

    public Optional<Store> findById(Long id);

    public Store save(Store store);

    public void delateById(Long id);

}
