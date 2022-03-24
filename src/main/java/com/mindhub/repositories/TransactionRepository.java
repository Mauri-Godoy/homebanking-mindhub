package com.mindhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.models.Transaction;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
