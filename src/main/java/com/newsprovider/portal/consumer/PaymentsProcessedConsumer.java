package com.newsprovider.portal.consumer;

import com.newsprovider.portal.DTO.PaymentDTO;
import com.newsprovider.portal.exception.UserNotFoundException;
import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.model.enums.PaymentStatus;
import com.newsprovider.portal.producer.RabbitProducer;
import com.newsprovider.portal.repository.PaymentRepository;
import com.newsprovider.portal.repository.SubscriptionRepository;
import com.newsprovider.portal.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;

@Component
public class PaymentsProcessedConsumer {

    @Value("${spring.rabbitmq.exchange.subscriptionNotification.name}")
    private String subscriptionNotificationExchangeName;

    @Value("${spring.rabbitmq.queue.subscriptionNotification.name}")
    private String subscriptionNotificationQueueName;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private RabbitProducer rabbitProducerService;

    @Autowired
    private UserRepository userRepository;



    @RabbitListener(queues = {"${spring.rabbitmq.queue.payments.processed.name}"})
    public void receive(@Payload PaymentDTO paymentDTO) throws Exception {
        process(paymentDTO);
    }

    @Transactional
    private void process(PaymentDTO paymentDTO) throws Exception {
        Payment payment = paymentRepository.findById(paymentDTO.id()).orElseThrow(UserNotFoundException::new);

        SubscriptionKind subscriptionKind = payment.getSubscriptionKind();

        Subscription subscription = new Subscription();
        subscription.setPaymentId(payment.getId());
        subscription.setInitialDate(Calendar.getInstance());
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, subscriptionKind.getDays());
        subscription.setEndDate(endDate);
        User user = payment.getUser();
        subscription.setUser(user);

        user.getSubscriptions().add(subscription);
        subscriptionRepository.save(subscription);

        userRepository.save(user);

        payment.setPaymentStatus(PaymentStatus.PROCESSED);
        payment.setProcessedDate(paymentDTO.processedDate());
        paymentRepository.save(payment);
        rabbitProducerService.produce(subscriptionNotificationExchangeName, subscriptionNotificationQueueName, subscription.getId());



        System.out.println("Message " + paymentDTO.toString());
    }
}
