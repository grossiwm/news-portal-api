package com.newsprovider.portal.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
        super("Payment not Found");
    }
}
