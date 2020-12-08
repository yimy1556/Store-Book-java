package com.storebook.storebook.repository;

import com.storebook.storebook.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
