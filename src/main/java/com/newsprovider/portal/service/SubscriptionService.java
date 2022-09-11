package com.newsprovider.portal.service;

import com.newsprovider.portal.exception.SubscriptionNotFoundException;
import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(SubscriptionNotFoundException::new);
    }

    public boolean isValid(Subscription subscription) {
        Calendar now = Calendar.getInstance();

        return subscription.getInitialDate().compareTo(now) <= 0
                && now.compareTo(subscription.getEndDate()) <= 0;

    }
}
