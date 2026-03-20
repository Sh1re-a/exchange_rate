package se.salt.foreignexchangeapi.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.ConversionRequest;
import se.salt.foreignexchangeapi.dto.ConversionResponse;
import se.salt.foreignexchangeapi.service.ConversionService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ConversionController {
    private final ConversionService conversionService;
    private static final Logger logger = LoggerFactory.getLogger(ConversionController.class);


    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /* @GetMapping("/conversion")
    public ConversionResponse convertResponse(
            @RequestParam CurrencyCode from,
            @RequestParam CurrencyCode to,
            @RequestParam String amount
    ) {
        return conversionService.convert(from, to, amount);
    }

     */
    @PostMapping("/conversions")
    public ResponseEntity<ConversionResponse> convert
            (@Valid @RequestBody ConversionRequest request,
            @RequestHeader(value = "User-Agent", required = false)
            String userAgent){

        logger.info("Incoming conversion request User-Agent={} body: baseCurrency={}, targetCurrency={}, amount={}"
                ,userAgent, request.from(),request.to(), request.amount());
        ConversionResponse response = conversionService.convert(request.from(),request.to(),request.amount());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/currencies")
    public ResponseEntity<Map<String,String>> getCurrencies(@RequestHeader(value = "User-Agent", required = false) String userAgent) {
        logger.info("Incoming get all currencies User-Agent={}", userAgent);
        Map<String, String> currencies = conversionService.getAllCurrencies();
        return ResponseEntity.ok().body(currencies);
    }
}
