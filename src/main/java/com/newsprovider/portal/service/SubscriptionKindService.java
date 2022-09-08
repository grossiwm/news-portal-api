package com.newsprovider.portal.service;

import com.newsprovider.portal.exception.SubscriptionKindNotFoundException;
import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.repository.SubscriptionKindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionKindService {

    @Autowired
    private SubscriptionKindRepository subscriptionKindRepository;

    public SubscriptionKind findByName(String name) {
        SubscriptionKind subscriptionKind = subscriptionKindRepository.findByName(name);
        if (Objects.isNull(subscriptionKind))
            throw new SubscriptionKindNotFoundException();
        return subscriptionKind;
    }

    public List<SubscriptionKind> findAll() {
        return subscriptionKindRepository.findAll();
    }

    public void save(SubscriptionKind subscriptionKind) {
        subscriptionKindRepository.save(subscriptionKind);
    }

    public void deleteAll() {
        subscriptionKindRepository.deleteAll();
    }
}
