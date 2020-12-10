package com.storebook.storebook.repository;

import com.storebook.storebook.entity.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreBookRepository extends JpaRepository<StoreBook, Long> {

    @Query(value = "SELECT s FROM StoreBook s WHERE s.book.id = ?1")
    List<StoreBook> findByBookId(long bookId);

    @Query(value = "SELECT s FROM StoreBook s WHERE s.store.id = ?1")
    List<StoreBook> findByStoreId(long storeId);
}
