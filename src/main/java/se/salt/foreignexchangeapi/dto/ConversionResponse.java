package se.salt.foreignexchangeapi.dto;

import java.math.BigDecimal;

public record ConversionResponse(
        String baseCurrency,
        String baseFullName,
        BigDecimal amount,
        String targetCurrency,
        String targetFullName,
        BigDecimal rate,
        BigDecimal result
) {
}
