package com.newsprovider.portal.model;

import com.newsprovider.portal.model.enums.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "request_date")
    private Calendar requestDate;

    @Column(name = "processedDate")
    private Calendar processedDate;

    @ManyToOne
    private User user;

    @Column
    private BigDecimal amount;

    @ManyToOne
    private SubscriptionKind subscriptionKind;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Transient
    private CreditCardDetails creditCardDetails;
}
