package se.salt.foreignexchangeapi.controller;

import jakarta.validation.Valid;
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
    private final ApiClient apiClient;

    public ConversionController(ConversionService conversionService, ApiClient apiClient) {
        this.conversionService = conversionService;
        this.apiClient = apiClient;
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
    public ResponseEntity<ConversionResponse> convert(@Valid @RequestBody ConversionRequest request){

        return ResponseEntity.ok(conversionService.convert(
                request.from(),
                request.to(),
                request.amount())
        );
    }

    @GetMapping("/currencies")
    public ResponseEntity<Map<String,String>> getCurrencies() {
        return ResponseEntity.ok(conversionService.getAllCurrencies());
    }
}
