package com.albionhelper.helper.api;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AlbionHttpClient {

    private final WebClient webClient;

    public AlbionHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable(value = "api", key = "#requestUrl")
    public String getResponse(String requestUrl) {
        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
