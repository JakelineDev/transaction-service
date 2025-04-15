package com.github.jakelinedev.transaction_service.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String userId;
    private String description;
    private double amount;
    private TransactionType type;
    private LocalDateTime date;

    public Transaction(String userId, String description, double amount, TransactionType type, LocalDateTime date) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }


}
