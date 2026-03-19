package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.ConversionResponse;

@Service
public class ConversionService {


    private final RateCacheService rateCacheService;

    public ConversionService(ApiClient apiClient, RateCacheService rateCacheService) {
        this.apiClient = apiClient;
        this.rateCacheService = rateCacheService;
    }

    public ConversionResponse convert(CurrencyCode baseCurrency, CurrencyCode wantedCurrency, String amount){
        double amountDouble = Double.parseDouble(amount);
        if(amountDouble <= 0){
            throw new IllegalArgumentException("Amount must be more than 0");
        }
        if(baseCurrency.equals(wantedCurrency)){
            return new ConversionResponse(
                    baseCurrency,
                    wantedCurrency,
                    amountDouble,
                    1,
                    amountDouble
            );
        }
        double rate = rateCacheService.getRate(baseCurrency, wantedCurrency);
        return new ConversionResponse(baseCurrency, wantedCurrency, amountDouble, rate, amountDouble * rate);
    }


}

