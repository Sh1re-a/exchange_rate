package se.salt.foreignexchangeapi.dto;

import java.util.Map;

public record FrankfurterConversionResponse(
        Double Amount,
        String base,
        String date,
        Map<String, Double> rates) { }
