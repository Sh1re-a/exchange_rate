package se.salt.foreignexchangeapi.dto;

public record RateConvertResponse(
        String baseCurrency,
        String wantedCurrency,
        double amount,
        double rate,
        double result
) {
}
