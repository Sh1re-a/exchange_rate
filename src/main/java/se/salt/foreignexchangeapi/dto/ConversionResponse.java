package se.salt.foreignexchangeapi.dto;

import se.salt.foreignexchangeapi.domain.CurrencyCode;

import java.math.BigDecimal;

public record ConversionResponse(
        CurrencyCode baseCurrency,
        CurrencyCode wantedCurrency,
        BigDecimal amount,
        BigDecimal rate,
        BigDecimal result
) {
}
