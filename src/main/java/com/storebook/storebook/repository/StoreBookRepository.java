package com.storebook.storebook.repository;

import com.storebook.storebook.entity.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreBookRepository extends JpaRepository<StoreBook, Long> {
}
