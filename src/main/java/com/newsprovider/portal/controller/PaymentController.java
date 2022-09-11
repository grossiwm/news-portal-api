package com.newsprovider.portal.controller;

import com.newsprovider.portal.model.CreditCardDetails;
import com.newsprovider.portal.model.Payment;
import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.model.User;
import com.newsprovider.portal.repository.LoggedUserRepository;
import com.newsprovider.portal.service.PaymentService;
import com.newsprovider.portal.service.SubscriptionKindService;
import com.newsprovider.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoggedUserRepository loggedUserRepository;

    @Autowired
    private SubscriptionKindService subscriptionKindService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/subscription/{subscriptionType}")
    public ResponseEntity<?> pay(@PathVariable String subscriptionType, @RequestBody CreditCardDetails creditCardDetails) {
        User user = loggedUserRepository.getAuthenticatedUser();
        if (userService.hasActiveSubscription(user) || userService.hasPendingPayments(user)) {
            return ResponseEntity.badRequest().build();
        }
        SubscriptionKind subscriptionKind = subscriptionKindService.findByName(subscriptionType);
        Payment payment = new Payment();
        payment.setAmount(subscriptionKind.getPrice());
        payment.setRequestDate(Calendar.getInstance());
        payment.setUser(user);
        payment.setSubscriptionKind(subscriptionKind);
        payment.setCreditCardDetails(creditCardDetails);

        paymentService.requestPayment(payment);

        return ResponseEntity.ok().build();
    }


}
