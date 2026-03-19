package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.ConversionResponse;

@Service
public class ConversionService {


    private final RateCacheService rateCacheService;

    public ConversionService(RateCacheService rateCacheService) {
        this.rateCacheService = rateCacheService;
    }

    public ConversionResponse convert(CurrencyCode baseCurrency, CurrencyCode targetCurrency, String amount){
        double amountDouble = Double.parseDouble(amount);
        if(amountDouble <= 0){
            throw new IllegalArgumentException("Amount must be more than 0");
        }
        if(baseCurrency.equals(targetCurrency)){
            return new ConversionResponse(
                    baseCurrency,
                    targetCurrency,
                    amountDouble,
                    1,
                    amountDouble
            );
        }
        double rate = rateCacheService.getRate(baseCurrency, targetCurrency);
        return new ConversionResponse(baseCurrency, targetCurrency, amountDouble, rate, amountDouble * rate);
    }


}

