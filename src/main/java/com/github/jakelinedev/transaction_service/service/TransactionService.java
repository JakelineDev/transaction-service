package com.github.jakelinedev.transaction_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.jakelinedev.transaction_service.dto.TransactionRequestDTO;
import com.github.jakelinedev.transaction_service.event.TransactionEventPublisher;
import com.github.jakelinedev.transaction_service.event.dto.TransactionEventDTO;
import com.github.jakelinedev.transaction_service.exception.TransactionNotFoundException;
import com.github.jakelinedev.transaction_service.model.Transaction;
import com.github.jakelinedev.transaction_service.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionEventPublisher eventPublisher;


    public ResponseEntity<Transaction> createTransaction(TransactionRequestDTO transactionDTO) {
    try {
        Transaction transaction = new Transaction();
        transaction.setUserId(transactionDTO.getUserId());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transaction.setDate(transactionDTO.getDate());

        repository.save(transaction);

        TransactionEventDTO eventDTO = new TransactionEventDTO();
        eventDTO.setId(transaction.getId());
        eventDTO.setUserId(transaction.getUserId());
        eventDTO.setDescription(transaction.getDescription());
        eventDTO.setAmount(transaction.getAmount());
        eventDTO.setType(transaction.getType());
        eventDTO.setTimestamp(transaction.getDate());

        eventPublisher.publish(eventDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}

    public ResponseEntity<Transaction> getTransactionById(String id) {

        Transaction transaction = repository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + id));
        return ResponseEntity.ok(transaction);
    }

    public ResponseEntity<List<Transaction>> getTransactionsByUser(String userId) {
        
        List<Transaction> transactions = repository.findByUserId(userId);
        
        if (transactions.isEmpty()) {
             return ResponseEntity.ok(new ArrayList<>()); 
         }

         return ResponseEntity.ok(transactions);

    }

    public ResponseEntity<Transaction> updateTransaction(String id, TransactionRequestDTO transactionDTO) {
        Optional<Transaction> optional = repository.findById(id);
    
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        Transaction transaction = optional.get();
        transaction.setUserId(transactionDTO.getUserId());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transaction.setDate(transactionDTO.getDate());
    
        return ResponseEntity.ok(repository.save(transaction));
    }
    

    public ResponseEntity<Void> deleteTransaction(String id) {

        Optional<Transaction> transaction = repository.findById(id);
        if(transaction.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build(); 
    }

}
