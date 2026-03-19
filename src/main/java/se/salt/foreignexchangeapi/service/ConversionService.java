package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.domain.CurrencyCode;
import se.salt.foreignexchangeapi.dto.ConversionResponse;
import se.salt.foreignexchangeapi.dto.CurrencyResponse;

import java.math.BigDecimal;

@Service
public class ConversionService {


    private final RateCacheService rateCacheService;

    public ConversionService(RateCacheService rateCacheService) {
        this.rateCacheService = rateCacheService;
    }

    public ConversionResponse convert(CurrencyCode baseCurrency, CurrencyCode targetCurrency, BigDecimal amount){
        if(baseCurrency.equals(targetCurrency)){
            return new ConversionResponse(
                    baseCurrency,
                    targetCurrency,
                    amount,
                    BigDecimal.valueOf(1),
                    amount
            );
        }
        double rate = rateCacheService.getRate(baseCurrency, targetCurrency);
        return new ConversionResponse(baseCurrency,
                targetCurrency,
                amount,
                BigDecimal.valueOf(rate),
                amount.multiply(BigDecimal.valueOf(rate)));
    }

    public CurrencyResponse getAllCurrencies(){
        return new CurrencyResponse(rateCacheService.getCurrencies());
    }


}

