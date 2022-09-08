package com.newsprovider.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "SubscriptionKind not found")
public class SubscriptionKindNotFoundException extends RuntimeException {
    public SubscriptionKindNotFoundException() {
        super("SubscriptionKind not Found");
    }
}
