package com.albionhelper.helper.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
public class BattleBoardHttpClient {

    private final WebClient webClient;

    public BattleBoardHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // 베틀 보드 가져올때 사용됨.
    public String getNoCache(String requestUrl) {
        String urlWithTimestamp = requestUrl + "&timestamp=" + Instant.now().getEpochSecond();
        return webClient.get()
                .uri(urlWithTimestamp)
                .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Object[] getResponseForEvent(String requestUrl) {
        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }

}
