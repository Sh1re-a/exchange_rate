package se.salt.foreignexchangeapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.service.FxService;

@RestController
@RequestMapping("/api")
public class TestController {
    private final ApiClient apiClient;
    private final FxService fxService;

    public TestController(ApiClient apiClient, FxService fxService) {
        this.apiClient = apiClient;
        this.fxService = fxService;
    }

    @GetMapping("/test")
    public RateConvertResponse convertResponse(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double dinero
    ) {
        return fxService.rateConvertResponse(from, to, dinero);
    }
}
