package com.storebook.storebook.service;

import com.storebook.storebook.entity.StoreBook;

import java.util.List;
import java.util.Optional;

public interface StoreBookService {
    public List<StoreBook> findAll();

    public Optional<StoreBook> findById(Long id);

    public StoreBook save(StoreBook storeBook);

    public void delateById(Long id);

    public List<StoreBook> findByBookId(long bookId);

    public List<StoreBook> findByStoreId(long stereId);
}
