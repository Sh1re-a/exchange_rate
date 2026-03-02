package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

@Service
public class FxService {

    private final ApiClient apiClient;

    public FxService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public RateConvertResponse rateConvertResponse(String baseCurrency, String wantedCurrency, double amount){
        FrankfurterLatestResponse frankfurterLatestResponse = apiClient.getRatesFromWantedCurrency(baseCurrency, wantedCurrency);
        double rate = frankfurterLatestResponse.rates().get(wantedCurrency);
        return new RateConvertResponse(baseCurrency, wantedCurrency, amount, rate, amount * rate);
    }
}

