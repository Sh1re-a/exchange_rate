package se.salt.foreignexchangeapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import se.salt.foreignexchangeapi.domain.CurrencyCode;

import java.math.BigDecimal;

public record ConversionRequest(
        @NotNull CurrencyCode from,
        @NotNull CurrencyCode to,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount
        ) {
}
