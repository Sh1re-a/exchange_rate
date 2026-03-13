package se.salt.foreignexchangeapi.dto;

import se.salt.foreignexchangeapi.domain.CurrencyCode;

public record RateConvertResponse(
        CurrencyCode baseCurrency,
        CurrencyCode wantedCurrency,
        double amount,
        double rate,
        double result
) {
}
