package se.salt.foreignexchangeapi.dto;

import java.util.Map;

public record CurrencyResponse(
        Map<String, String> currencies
) {

}
