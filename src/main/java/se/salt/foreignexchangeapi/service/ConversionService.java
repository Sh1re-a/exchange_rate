package se.salt.foreignexchangeapi.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.RateConvertResponse;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

@Service
public class ConversionService {

    private final ApiClient apiClient;
    private int count = 0;

    public ConversionService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public RateConvertResponse rateConvertResponse(CurrencyCode baseCurrency, CurrencyCode wantedCurrency, String amount){
        double amountDouble = Double.parseDouble(amount);
        if(amountDouble <= 0){
            throw new IllegalArgumentException("Amount must be more than 0");
        }
        if(baseCurrency.equals(wantedCurrency)){
            return new RateConvertResponse(
                    baseCurrency,
                    wantedCurrency,
                    amountDouble,
                    1,
                    amountDouble
            );
        }
        FrankfurterLatestResponse frankfurterLatestResponse = apiClient.getRatesFromWantedCurrency(baseCurrency.name(), wantedCurrency.name());
        double rate = getRate(baseCurrency, wantedCurrency);
        return new RateConvertResponse(baseCurrency, wantedCurrency, amountDouble, rate, amountDouble * rate);
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

