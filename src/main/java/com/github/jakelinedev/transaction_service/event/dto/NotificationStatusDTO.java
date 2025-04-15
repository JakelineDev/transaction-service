package com.github.jakelinedev.transaction_service.event.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class NotificationStatusDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String userId;
    private boolean success;
    private String message;

}
