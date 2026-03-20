package se.salt.foreignexchangeapi.dto;

import java.math.BigDecimal;

public record ConversionResponse(
        String baseCurrency,
        String wantedCurrency,
        String fullName,
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal result
) {
}
