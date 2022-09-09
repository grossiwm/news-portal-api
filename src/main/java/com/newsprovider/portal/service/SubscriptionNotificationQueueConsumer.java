package com.newsprovider.portal.service;

import com.newsprovider.portal.model.Subscription;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionNotificationQueueConsumer {

    @Autowired
    private SubscriptionService subscriptionService;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.subscriptionNotification.name}"})
    public void receive(@Payload Long id) throws Exception {
        Subscription subscription = subscriptionService.findById(id);
        subscriptionService.notifyUserOfSubscription(subscription);
    }

}
