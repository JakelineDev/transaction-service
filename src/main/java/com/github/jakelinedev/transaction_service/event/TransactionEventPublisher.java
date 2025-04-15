package com.github.jakelinedev.transaction_service.event;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jakelinedev.transaction_service.config.RabbitMQConfig;
import com.github.jakelinedev.transaction_service.event.dto.TransactionEventDTO;

@Service
public class TransactionEventPublisher {

   @Autowired
    RabbitTemplate rabbitTemplate;

    public void publish(TransactionEventDTO eventDTO) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.TRANSACTION_EXCHANGE,
            RabbitMQConfig.TRANSACTION_ROUTING_KEY,
            eventDTO
        );
    }

}
