package se.salt.foreignexchangeapi.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

import java.util.Map;

@Service
public class RateCacheService {

    private final ApiClient apiClient;
    private int count = 0;

    public RateCacheService(ApiClient apiClient) {
        this.apiClient = apiClient;

    }

    @Cacheable("rates")
    public double getRate(CurrencyCode baseCurrency, CurrencyCode wantedCurrency) {
        FrankfurterConversionResponse response =
                apiClient.getRatesFromWantedCurrency(baseCurrency.name(), wantedCurrency.name());
        count++;
        System.out.println("Calling Frankfurter API " + count);
        return response.rates().get(wantedCurrency.name());
    }

    public Map<String, String> getCurrencies(){

        return apiClient.getAllCurrenciesAndTheirName();


    }
}
