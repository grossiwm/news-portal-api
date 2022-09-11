package com.newsprovider.portal.loader;

import com.newsprovider.portal.model.SubscriptionKind;
import com.newsprovider.portal.service.SubscriptionKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("!prod")
public class SubscriptionKindsLoader implements ApplicationRunner {

    @Autowired
    private SubscriptionKindService subscriptionKindService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        subscriptionKindService.save(new SubscriptionKind(null, 30, new BigDecimal("9.99"), "monthly", null));
        subscriptionKindService.save(new SubscriptionKind(null, 365, new BigDecimal("9.99"), "yearly", null));
    }
}
