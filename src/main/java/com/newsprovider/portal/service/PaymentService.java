package com.newsprovider.portal.service;

import com.newsprovider.portal.DTO.PaymentDTO;
import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PaymentService {

    @Value("${spring.rabbitmq.queue.payments.toProcess.name}")
    private String paymentsToProcessQueueName;

    @Value("${spring.rabbitmq.exchange.payments.name}")
    private String paymentsExchangeName;

    @Autowired
    private RabbitProducerService producerService;

    @Autowired
    private PaymentRepository paymentRepository;

    public void requestPayment(Payment payment) {
        sendToQueueAndPersistInDB(payment);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }


    private void send(PaymentDTO payment) {

        producerService.produce(paymentsExchangeName, paymentsToProcessQueueName, payment);
    }

    @Transactional
    private void sendToQueueAndPersistInDB(Payment payment) {
        paymentRepository.save(payment);
        send(new PaymentDTO(
                payment.getId(),
                payment.getRequestDate(),
                null,
                payment.getAmount())
        );
    }
}
