package com.newsprovider.portal.controller;

import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.service.PaymentService;
import com.newsprovider.portal.service.SubscriptionKindService;
import com.newsprovider.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionKindService subscriptionKindService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/subscription/{subscriptionType}")
    public ResponseEntity<?> pay(@PathVariable String subscriptionType) {
        SubscriptionKind subscriptionKind = subscriptionKindService.findByName(subscriptionType);
        Payment payment = new Payment();
        payment.setAmount(subscriptionKind.getPrice());
        payment.setRequestDate(Calendar.getInstance());
        payment.setUser(userService.getAuthenticatedUser());
        payment.setSubscriptionKind(subscriptionKind);

        paymentService.requestPayment(payment);

        return ResponseEntity.ok().build();
    }


}
