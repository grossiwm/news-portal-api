package com.newsprovider.portal.service;

import com.newsprovider.portal.exception.SubscriptionNotFoundException;
import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.repository.SubscriptionRepository;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private RabbitProducerService rabbitProducerService;

    @Value("${spring.rabbitmq.exchange.subscriptionNotification.name}")
    private String subscriptionNotificationExchangeName;

    @Value("${spring.rabbitmq.queue.subscriptionNotification.name}")
    private String subscriptionNotificationQueueName;


    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(SubscriptionNotFoundException::new);
    }

    public void sendToNotificationQueue(Subscription subscription) {
        rabbitProducerService.produce(subscriptionNotificationExchangeName, subscriptionNotificationQueueName, subscription.getId());
    }

    public void notifyUserOfSubscription(Subscription subscription) throws IOException {

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        User user = subscription.getUser();
        String subject = "PREMIUM ACCOUNT ACTIVATED";
        Email to = new Email(user.getEmail());

        String contentPayload = "Hi " + user.getName() + ",\nYour are now a member of our " +
                "Premium Account.\nYou subscription started at " +
                "" + simpleDateFormat.format(subscription.getInitialDate().getTime()) + "" +
                " and ends at " + simpleDateFormat.format(subscription.getEndDate().getTime()) + "" +
                "\nRegards.";

        Content content = new Content("text/plain", contentPayload);
        Mail mail = new Mail(null, subject, to, content);
        mailService.send(mail);
    }

}
