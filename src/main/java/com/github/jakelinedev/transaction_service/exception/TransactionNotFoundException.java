package com.github.jakelinedev.transaction_service.exception;

public class TransactionNotFoundException extends RuntimeException {

        public TransactionNotFoundException(String message) {
            super(message);
        }

}
