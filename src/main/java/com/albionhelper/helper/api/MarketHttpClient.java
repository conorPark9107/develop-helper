package com.albionhelper.helper.api;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class MarketHttpClient {

    @Cacheable(value = "market", key = "#requestUrl")
    public String getResponse(String requestUrl) {
        
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // 버퍼사이즈 -1 : unlimited
                .build();

        WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();
        return webClient.get()
                .uri(requestUrl)
                .header("x-test", "header")
                .retrieve()
                .bodyToMono(String.class)
                .delaySubscription(Duration.ofMillis(300))
                .block();

    }

}
