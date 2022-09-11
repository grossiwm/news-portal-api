package com.newsprovider.portal.service;

import com.newsprovider.portal.DTO.PaymentDTO;
import com.newsprovider.portal.exception.PaymentNotFoundException;
import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.model.enums.PaymentStatus;
import com.newsprovider.portal.producer.RabbitProducer;
import com.newsprovider.portal.repository.PaymentRepository;
import com.newsprovider.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService {

    @Value("${spring.rabbitmq.queue.payments.toProcess.name}")
    private String paymentsToProcessQueueName;

    @Value("${spring.rabbitmq.exchange.payments.name}")
    private String paymentsExchangeName;

    @Autowired
    private RabbitProducer producerService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public void requestPayment(Payment payment) {
        sendToQueueAndPersistInDB(payment);
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }


    private void send(PaymentDTO payment) {

        producerService.produce(paymentsExchangeName, paymentsToProcessQueueName, payment);
    }

    public List<Payment> findByPaymentStatusAndUser(PaymentStatus paymentStatus, User user) {
        return paymentRepository.findByPaymentStatusAndUser(paymentStatus, user);
    }

    public List<Payment> findByUser(User user) {
        return paymentRepository.findByUser(user);
    }
    @Transactional
    private void sendToQueueAndPersistInDB(Payment payment) {


        payment.setPaymentStatus(PaymentStatus.REQUESTED);

        paymentRepository.save(payment);
        User user = payment.getUser();
        user.getPayments().add(payment);
        userRepository.save(user);

        send(new PaymentDTO(
                payment.getId(),
                payment.getRequestDate(),
                null,
                payment.getAmount(),
                payment.getCreditCardDetails())
        );
    }
}
