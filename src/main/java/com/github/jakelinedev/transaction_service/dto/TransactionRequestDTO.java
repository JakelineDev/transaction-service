package com.github.jakelinedev.transaction_service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.github.jakelinedev.transaction_service.model.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransactionRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "User ID cannot be null")
    private String userId;

    @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    private LocalDateTime date = LocalDateTime.now(); 

}
