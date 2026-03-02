package se.salt.foreignexchangeapi.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import se.salt.foreignexchangeapi.dto.FrankfurterLatestResponse;

@Configuration
public class ApiClient {

    private final RestClient restClient;

    public ApiClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public FrankfurterLatestResponse convertCurrency(String baseCurrency, String wantedCurrency){
        return restClient.get().uri("/latest?base=" + baseCurrency+ "&symbols=" + wantedCurrency)
                .retrieve()
                .body(FrankfurterLatestResponse.class);
    }
}
