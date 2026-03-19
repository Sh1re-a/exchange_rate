package se.salt.foreignexchangeapi.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.salt.foreignexchangeapi.dto.FrankfurterConversionResponse;

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


}
