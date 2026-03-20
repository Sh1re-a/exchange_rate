package se.salt.foreignexchangeapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConversionRequest(
        @NotNull
        @NotBlank(message = "From currency is required") String from,
        @NotNull
        @NotBlank(message = "To currency is required")String to,
        @NotNull
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount
        ) {
}
