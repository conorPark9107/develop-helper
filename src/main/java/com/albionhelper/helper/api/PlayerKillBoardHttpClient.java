package com.albionhelper.helper.api;

import com.albionhelper.helper.domain.battle.Event;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
public class PlayerKillBoardHttpClient {

    private final WebClient webClient;

    public PlayerKillBoardHttpClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // getDetail()메서드로부터 호출되며, 1 v 1 이벤트(킬상세)로그를 파싱하여 리턴.
    @Cacheable(value = "player", key = "#requestUrl")
    public Event[] getResponseEvent(String requestUrl) {

        System.out.println("1v1상세 페이지 api 호출.");

        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(Event[].class)
                .block();
    }

    @Cacheable(value = "player", key = "#requestUrl")
    public String getResponseForBoard(String requestUrl) {

        System.out.println("API호출 2번 메서드");

        String urlWithTimestamp = requestUrl + "?timestamp=" + Instant.now().getEpochSecond();
        return webClient.get()
                .uri(urlWithTimestamp)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Cacheable(value = "player", key = "#requestUrl")
    public String getResponse(String requestUrl) {

        System.out.println("API호출 3번 메서드");

        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//                .block(Duration.ofMillis(10_000));
    }
}
