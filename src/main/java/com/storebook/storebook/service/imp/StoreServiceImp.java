package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.Store;
import com.storebook.storebook.repository.StoreRepository;
import com.storebook.storebook.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImp implements StoreService{
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> findAll() { return storeRepository.findAll(); }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Store save(Store store) { return storeRepository.save(store); }

    @Override
    public void delateById(Long id) {
        storeRepository.deleteById(id);
    }
}
