package com.github.jakelinedev.transaction_service.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.github.jakelinedev.transaction_service.event.dto.NotificationStatusDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionEventConsumer {

    @RabbitListener(queues = "notification.status.queue")
    public void receiveMessage(NotificationStatusDTO status) {
        if (status.isSuccess()) {
            log.info("Nnotification was successfully sent to user {} in transaction {}",
                     status.getUserId(), status.getTransactionId());
        } else {
            log.warn("Error while send notification to user {} in transaction {}: {}",
                     status.getUserId(), status.getTransactionId(), status.getMessage());
        }
    }
    
}
