package se.salt.foreignexchangeapi.service;

import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.dto.ConversionResponse;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ConversionService {


    private final RateCacheService rateCacheService;

    public ConversionService(RateCacheService rateCacheService) {
        this.rateCacheService = rateCacheService;
    }

    public ConversionResponse convert(String baseCurrency, String targetCurrency, BigDecimal amount){
        Map<String , String > currencies = rateCacheService.getCurrencies();

        if(baseCurrency.equals(targetCurrency)){
            return new ConversionResponse(
                    baseCurrency.toUpperCase(),
                    currencies.get(baseCurrency),
                    amount,
                    targetCurrency.toUpperCase(),
                    currencies.get(targetCurrency),
                    BigDecimal.valueOf(1),
                    amount
            );
        }
        double rate = rateCacheService.getRate(baseCurrency, targetCurrency);
        return new ConversionResponse(
                baseCurrency.toUpperCase(),
                currencies.get(baseCurrency),
                amount,
                targetCurrency.toUpperCase(),
                currencies.get(targetCurrency),
                BigDecimal.valueOf(rate),
                amount.multiply(BigDecimal.valueOf(rate)));
    }

    public Map<String, String> getAllCurrencies(){

        return rateCacheService.getCurrencies();
    }



}

