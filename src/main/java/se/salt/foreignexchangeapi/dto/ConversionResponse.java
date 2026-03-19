package se.salt.foreignexchangeapi.dto;

import se.salt.foreignexchangeapi.domain.CurrencyCode;

public record ConversionResponse(
        CurrencyCode baseCurrency,
        CurrencyCode wantedCurrency,
        double amount,
        double rate,
        double result
) {
}
