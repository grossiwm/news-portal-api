package com.newsprovider.portal.service;

import com.newsprovider.portal.DTO.PaymentDTO;
import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.model.enums.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;

@Component
public class PaymentsProcessedReceiver {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;



    @RabbitListener(queues = {"${spring.rabbitmq.queue.payments.processed.name}"})
    public void receive(@Payload PaymentDTO paymentDTO) throws Exception {
        process(paymentDTO);
    }

    @Transactional
    private void process(PaymentDTO paymentDTO) throws Exception {
        Payment payment = paymentService.findById(paymentDTO.id());

        SubscriptionKind subscriptionKind = payment.getSubscriptionKind();

        Subscription subscription = new Subscription();
        subscription.setPaymentId(payment.getId());
        subscription.setInitialDate(Calendar.getInstance());
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, subscriptionKind.getDays());
        subscription.setEndDate(endDate);
        subscription.setUser(payment.getUser());

        payment.setPaymentStatus(PaymentStatus.PROCESSED);
        paymentService.save(payment);
        subscriptionService.save(subscription);

        subscriptionService.sendToNotificationQueue(subscription);

        System.out.println("Message " + paymentDTO.toString());
    }
}
