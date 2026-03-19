package se.salt.foreignexchangeapi.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

import java.util.Map;

@Component
public class ApiClient {

    private final RestClient restClient;

    public ApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public FrankfurterConversionResponse getRatesFromWantedCurrency(String baseCurrency, String wantedCurrency){
        return restClient.get().uri("/latest?base=" + baseCurrency+ "&symbols=" + wantedCurrency)
                .retrieve()
                .body(FrankfurterConversionResponse.class);
    }

    public Map<String, String> getAllCurrenciesAndTheirName(){
        return restClient.get()
                .uri("/currencies")
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, String>>() {
                });
    }


}
