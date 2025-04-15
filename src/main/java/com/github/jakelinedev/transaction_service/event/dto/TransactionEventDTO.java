package com.github.jakelinedev.transaction_service.event.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.github.jakelinedev.transaction_service.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEventDTO implements Serializable  {
    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String description;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;

}
