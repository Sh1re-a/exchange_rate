package se.salt.foreignexchangeapi.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

@Component
public class ApiClient {

    private final RestClient restClient;

    public ApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public FrankfurterLatestResponse getRatesFromWantedCurrency(String baseCurrency, String wantedCurrency){
        return restClient.get().uri("/latest?base=" + baseCurrency+ "&symbols=" + wantedCurrency)
                .retrieve()
                .body(FrankfurterLatestResponse.class);
    }
}
