package com.github.jakelinedev.transaction_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakelinedev.transaction_service.TestSecurityConfig;
import com.github.jakelinedev.transaction_service.model.Transaction;
import com.github.jakelinedev.transaction_service.model.TransactionType;
import com.github.jakelinedev.transaction_service.service.TransactionService;

@WebMvcTest(TransactionController.class)
@Import(TestSecurityConfig.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

     private Transaction mockTransaction() {
        return new Transaction(
            "abc123",
            "user123",
            "Market shopping",
            1000.0,
            TransactionType.EXPENSE,
            LocalDateTime.now()
        );
    }

    @Test
    void testGetTransactionByIdSuccess() throws Exception {
        Transaction dto = mockTransaction();

        when(transactionService.getTransactionById("abc123")).thenReturn(ResponseEntity.ok(dto));

        mockMvc.perform(get("/api/transactions/abc123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Market shopping"))
            .andExpect(jsonPath("$.amount").value(1000.0));
    }

    @Test
    void testCreateTransactionSuccess() throws Exception {
        Transaction dto = mockTransaction();

        when(transactionService.createTransaction(any()))
         .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(dto));

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("abc123"));
    }

    @Test
    void testUpdateTransactionSuccess() throws Exception {
        Transaction updatedDto = mockTransaction();
        updatedDto.setDescription("Market shopping");

        when(transactionService.updateTransaction(any(), any())).thenReturn(ResponseEntity.ok(updatedDto));

        mockMvc.perform(put("/api/transactions/abc123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Market shopping"));
    }

    @Test
    void testDeleteTransactionSuccess() throws Exception {
        mockMvc.perform(delete("/api/transactions/abc123"))
            .andExpect(status().isOk());
    }

}
