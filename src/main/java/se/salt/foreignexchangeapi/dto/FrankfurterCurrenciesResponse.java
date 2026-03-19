package se.salt.foreignexchangeapi.dto;

import java.util.Map;

public record FrankfurterCurrenciesResponse(
        Map<String, String> currencies
) {
}
