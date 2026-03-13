package se.salt.foreignexchangeapi.service;

import org.springframework.cache.annotation.Cacheable;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

public class RateCacheService {

    private final ApiClient apiClient;
    private int count = 0;

    public RateCacheService(ApiClient apiClient, int count) {
        this.apiClient = apiClient;
        this.count = count;
    }

    @Cacheable("rates")
    public double getRate(CurrencyCode baseCurrency, CurrencyCode wantedCurrency) {
        FrankfurterLatestResponse response =
                apiClient.getRatesFromWantedCurrency(baseCurrency.name(), wantedCurrency.name());
        count++;
        System.out.println("Calling Frankfurter API " + count);

        return response.rates().get(wantedCurrency.name());

    }
}
