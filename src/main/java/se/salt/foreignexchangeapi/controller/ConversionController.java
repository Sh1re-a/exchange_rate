package se.salt.foreignexchangeapi.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.ConversionRequest;
import se.salt.foreignexchangeapi.dto.ConversionResponse;
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
    public ConversionResponse convertResponse(
            @RequestParam CurrencyCode from,
            @RequestParam CurrencyCode to,
            @RequestParam String amount
    ) {
        return conversionService.convert(from, to, amount);
    }

    @PostMapping("/conversions")
    public ConversionResponse convert(@Valid @RequestBody ConversionRequest request){
        return conversionService.convert(
                request.from(),
                request.to(),
                request.amount()
        );
    }

    @GetMapping("/currencies")
    public CurrencyCode[] getCurrencies() {
        return CurrencyCode.values();
    }
}
