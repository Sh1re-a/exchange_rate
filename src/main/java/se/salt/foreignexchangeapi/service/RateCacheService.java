package se.salt.foreignexchangeapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

import java.util.Map;
import java.util.Optional;


@Service
public class RateCacheService {

    private final ApiClient apiClient;
    private int countRate = 0;
    private int countCurrencies = 0;
    private final static Logger logger = LoggerFactory.getLogger(RateCacheService.class);

    public RateCacheService(ApiClient apiClient) {
        this.apiClient = apiClient;

    }

    @Cacheable("rates")
    public double getRate(String baseCurrency, String wantedCurrency) {
        FrankfurterConversionResponse response =
                apiClient.getRatesFromWantedCurrency(baseCurrency, wantedCurrency);
        countRate++;
        logger.info("Calling External Frankfurter API getRate() count={}", countRate);
        return Optional.ofNullable(response.rates().get(wantedCurrency))
                .orElseThrow(() -> new IllegalStateException("Could not fetch rate from API"));
    }
    @Cacheable("currencies")
    public Map<String, String> getCurrencies(){
        countCurrencies++;
        logger.info("Calling External Frankfurter API getCurrencies() count={}", countCurrencies);
        return Optional.ofNullable(apiClient.getAllCurrenciesAndTheirName())
                .orElseThrow(()  -> new IllegalStateException("Could not fetch currencies from API"));
    }
}
