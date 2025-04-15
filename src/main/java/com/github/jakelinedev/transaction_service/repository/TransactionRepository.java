package com.github.jakelinedev.transaction_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.jakelinedev.transaction_service.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
 
    List<Transaction> findByUserId(String userId);
}
