package se.salt.foreignexchangeapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import se.salt.foreignexchangeapi.client.ApiClient;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

import java.util.Map;
import java.util.Optional;


@Service
public class RateCacheService {

    private final ApiClient apiClient;
    private int countRate = 0;
    private int countCurrencies = 0;
    private final static Logger logger = LoggerFactory.getLogger(RateCacheService.class);

    public RateCacheService(ApiClient apiClient) {
        this.apiClient = apiClient;

    }

    @Cacheable("rates")
    public double getRate(String baseCurrency, String wantedCurrency) {
        FrankfurterConversionResponse response =
                apiClient.getRatesFromWantedCurrency(baseCurrency, wantedCurrency);
        countRate++;
        logger.info("Calling External Frankfurter API getRate() count={}", countRate);
        Double rate = response.rates().get(wantedCurrency);
        if(rate == null){
            logger.warn("Could not fetch rate from External API");
            throw new IllegalStateException("Could not fetch rate from external API");
        }
        return rate;
    }
    @Cacheable("currencies")
    public Map<String, String> getCurrencies(){
        countCurrencies++;
        logger.info("Calling External Frankfurter API getCurrencies() count={}", countCurrencies);
        Map<String, String> currencies = apiClient.getAllCurrenciesAndTheirName();
        if(currencies == null){
            logger.warn("Could not fetch currencies from external API");
            throw new IllegalStateException("Could not fetch currencies from external API");
        }
        return currencies;
    }
}
