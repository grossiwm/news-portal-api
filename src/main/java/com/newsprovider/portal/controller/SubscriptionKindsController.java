package com.newsprovider.portal.controller;

import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.service.SubscriptionKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscription-kinds")
public class SubscriptionKindsController {

    @Autowired
    private SubscriptionKindService subscriptionKindService;

    @GetMapping
    public List<SubscriptionKind> getSubscriptionKinds() {
        return subscriptionKindService.findAll();
    }
}
