package com.newsprovider.portal.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void produce(String exchange, String routingKey, Object messageData) {
        amqpTemplate.convertAndSend(exchange, routingKey, messageData);
    }
}
