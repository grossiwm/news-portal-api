package com.newsprovider.portal.repository;

import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPaymentStatusAndUser(PaymentStatus paymentStatus, User user);

    List<Payment> findByUser(User user);

}
