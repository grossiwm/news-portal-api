package com.newsprovider.portal.consumer;

import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.service.MailService;
import com.newsprovider.portal.service.SubscriptionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionNotificationConsumer {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MailService mailService;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.subscriptionNotification.name}"})
    public void receive(@Payload Long id) throws Exception {
        Subscription subscription = subscriptionService.findById(id);
        mailService.notifyUserOfSubscription(subscription);
    }

}
