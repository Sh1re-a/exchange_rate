package se.salt.foreignexchangeapi.controller;

import org.springframework.web.bind.annotation.*;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.service.ConversionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/conversion")
    public RateConvertResponse convertResponse(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String amount
    ) {
        return conversionService.rateConvertResponse(from, to, amount);
    }
}
