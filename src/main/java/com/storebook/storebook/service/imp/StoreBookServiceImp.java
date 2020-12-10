package com.storebook.storebook.service.imp;

import com.storebook.storebook.entity.StoreBook;
import com.storebook.storebook.repository.StoreBookRepository;
import com.storebook.storebook.service.StoreBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreBookServiceImp implements StoreBookService {
    @Autowired
    private StoreBookRepository storeBookRepository;

    @Override
    public List<StoreBook> findAll() {
        return storeBookRepository.findAll();
    }

    @Override
    public Optional<StoreBook> findById(Long id) {
        return storeBookRepository.findById(id);
    }

    @Override
    public StoreBook save(StoreBook storeBook) {
        return storeBookRepository.save(storeBook);
    }

    @Override
    public void delateById(Long id) {
        storeBookRepository.deleteById(id);
    }

    @Override
    public List<StoreBook> findByBookId(long bookId) {
        return storeBookRepository.findByBookId(bookId);
    }

    @Override
    public List<StoreBook> findByStoreId(long stereId) {
        return storeBookRepository.findByStoreId(stereId);
    }
}
