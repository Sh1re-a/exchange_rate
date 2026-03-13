package se.salt.foreignexchangeapi.controller;

import org.springframework.web.bind.annotation.*;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.service.FxService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ConversionController {
    private final ApiClient apiClient;
    private final FxService fxService;

    public ConversionController(ApiClient apiClient, FxService fxService) {
        this.apiClient = apiClient;
        this.fxService = fxService;
    }

    @GetMapping("/conversion")
    public RateConvertResponse convertResponse(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String amount
    ) {
        return fxService.rateConvertResponse(from, to, amount);
    }
}
