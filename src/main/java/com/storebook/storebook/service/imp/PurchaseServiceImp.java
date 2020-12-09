package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.Purchase;
import com.storebook.storebook.repository.BookRepository;
import com.storebook.storebook.repository.PurchaseRepository;
import com.storebook.storebook.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImp implements PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public void delateById(Long id) {
        purchaseRepository.deleteById(id);
    }
}
