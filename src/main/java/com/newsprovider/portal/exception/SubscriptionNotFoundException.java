package com.newsprovider.portal.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException() {
        super("Subscription not Found");
    }
}
