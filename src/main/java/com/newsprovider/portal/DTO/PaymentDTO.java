package com.newsprovider.portal.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public record PaymentDTO(Long id, Calendar requestDate, Calendar processedDate, BigDecimal amount) implements Serializable {
}
