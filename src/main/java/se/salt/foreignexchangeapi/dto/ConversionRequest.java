package se.salt.foreignexchangeapi.dto;

import org.jspecify.annotations.NonNull;
import se.salt.foreignexchangeapi.domain.CurrencyCode;

public record ConversionRequest(
        @NonNull CurrencyCode from,
        @NonNull CurrencyCode to,
        @NonNull @DecimalMin()
        ) {
}
