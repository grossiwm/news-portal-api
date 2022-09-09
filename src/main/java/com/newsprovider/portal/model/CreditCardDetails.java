package com.newsprovider.portal.model;

import lombok.Data;

@Data
public class CreditCardDetails {
    private String holder;
    private String number;
    private String validThru;
    private String securityCode;
}
