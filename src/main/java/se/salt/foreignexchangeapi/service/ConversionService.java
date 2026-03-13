package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

@Service
public class ConversionService {

    private final ApiClient apiClient;

    public ConversionService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public RateConvertResponse rateConvertResponse(CurrencyCode baseCurrency, CurrencyCode wantedCurrency, String amount){
        double amountDouble = Double.parseDouble(amount);
        FrankfurterLatestResponse frankfurterLatestResponse = apiClient.getRatesFromWantedCurrency(baseCurrency.name(), wantedCurrency.name());
        double rate = frankfurterLatestResponse.rates().get(wantedCurrency);
        return new RateConvertResponse(baseCurrency, wantedCurrency, amountDouble, rate, amountDouble * rate);
    }
}

