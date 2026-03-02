package se.salt.foreignexchangeapi.dto;

import java.util.Map;

public record FrankfurterLatestResponse(
        double Amount,
        String base,
        String date,
        Map<String, Double> rates) { }
