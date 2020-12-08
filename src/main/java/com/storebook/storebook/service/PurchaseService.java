package com.storebook.storebook.service;

import com.storebook.storebook.entity.Customer;
import com.storebook.storebook.entity.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    public List<Purchase> findAll();

    public Optional<Purchase> findById(Long id);

    public Purchase save(Purchase purchase);

    public void delateById(Long id);

}
