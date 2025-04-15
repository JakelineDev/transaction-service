package com.github.jakelinedev.transaction_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String TRANSACTION_EXCHANGE = "transaction.exchange";
    public static final String TRANSACTION_ROUTING_KEY = "transaction.created";
    public static final String TRANSACTION_QUEUE = "transaction.created.queue";


    public static final String NOTIFICATION_STATUS_EXCHANGE = "notification.status.exchange";
    public static final String NOTIFICATION_STATUS_ROUTING_KEY = "notification.status";
    public static final String NOTIFICATION_STATUS_QUEUE = "notification.status.queue";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue(TRANSACTION_QUEUE, true);
    }

    @Bean
    public DirectExchange transactionExchange() {
        return new DirectExchange(TRANSACTION_EXCHANGE);
    }

    @Bean
    public Binding transactionBinding() {
        return BindingBuilder
                .bind(transactionQueue())
                .to(transactionExchange())
                .with(TRANSACTION_ROUTING_KEY);
    }

    @Bean
    public Queue notificationStatusQueue() {
        return new Queue(NOTIFICATION_STATUS_QUEUE, true);
    }

    @Bean
    public DirectExchange notificationStatusExchange() {
        return new DirectExchange(NOTIFICATION_STATUS_EXCHANGE);
    }

    @Bean
    public Binding notificationStatusBinding() {
        return BindingBuilder
                .bind(notificationStatusQueue())
                .to(notificationStatusExchange())
                .with(NOTIFICATION_STATUS_ROUTING_KEY);
    }
}
