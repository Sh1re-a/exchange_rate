package se.salt.foreignexchangeapi.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

import java.util.Map;
import java.util.Optional;

@Service
public class RateCacheService {

    private final ApiClient apiClient;
    private int countRate = 0;
    private int countCurrencies = 0;

    public RateCacheService(ApiClient apiClient) {
        this.apiClient = apiClient;

    }

    @Cacheable("rates")
    public double getRate(CurrencyCode baseCurrency, CurrencyCode wantedCurrency) {
        FrankfurterConversionResponse response =
                apiClient.getRatesFromWantedCurrency(baseCurrency.name(), wantedCurrency.name());
        countRate++;
        System.out.println("Calling Frankfurter API getRate() " + countRate);
        return Optional.ofNullable(response.rates().get(wantedCurrency.name()))
                .orElseThrow(() -> new IllegalStateException("Could not fetch rate from API"));
    }
    @Cacheable("currencies")
    public Map<String, String> getCurrencies(){
        countCurrencies++;
        System.out.println("Calling Frankfurter API getCurrencies() " + countCurrencies);
        return Optional.ofNullable(apiClient.getAllCurrenciesAndTheirName())
                .orElseThrow(()  -> new IllegalStateException("Could not fetch currencies from API"));
    }
}
