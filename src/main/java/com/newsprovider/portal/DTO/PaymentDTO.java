package com.newsprovider.portal.DTO;

import com.newsprovider.portal.model.CreditCardDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public record PaymentDTO(Long id, Calendar requestDate, Calendar processedDate, BigDecimal amount, CreditCardDetails creditCardDetails) implements Serializable {
}
