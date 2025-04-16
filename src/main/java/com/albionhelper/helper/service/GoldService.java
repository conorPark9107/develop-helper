package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.gold.Gold;
import com.albionhelper.helper.enums.ServerRegion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GoldService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String getResponse(String url) {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // 버퍼사이즈 -1 : unlimited
                .build();

        WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();
        return  webClient.get()
                .uri(url)
                .header("x-test", "header")
                .retrieve()
                .bodyToMono(String.class)
                .delaySubscription(Duration.ofMillis(300))
                .block();
    }

    // https://west.albion-online-data.com/api/v2/stats/gold?date=2-5-2020&end_date=2-12-2020
    public List<Gold> getGoldPriciesOnEast() throws JsonProcessingException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = ServerRegion.from("EAST_GOLD").getUrl() + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request EAST : {}", url);
        String response = getResponse(url);
        return getPriceList(response);
    }

    public List<Gold> getGoldPriciesOnWest() throws JsonProcessingException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = ServerRegion.from("WEST_GOLD").getUrl() + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request WEST : {}", url);
        String response = getResponse(url);
        return getPriceList(response);
    }

    public List<Gold> getGoldPriciesOnEurope() throws JsonProcessingException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = ServerRegion.from("EUROPE_GOLD").getUrl() + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request EU : {}", url);
        String response = getResponse(url);
        return getPriceList(response);
    }

    private List<Gold> getPriceList(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNodes = objectMapper.readTree(response);
        List<Gold> list = new ArrayList<>();

        Map<String, Integer> map = new LinkedHashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd");

        for (int i = 0; i < rootNodes.size(); i++) {
            JsonNode node = rootNodes.get(i);
            Gold p = objectMapper.treeToValue(node, Gold.class);
            String key = formatters.format(p.getTimeStamp());
            map.put(key, map.getOrDefault(key, 0) + p.getPrice());
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        }

        for (String key : map.keySet()) {
            list.add(new Gold(map.get(key) / countMap.get(key), key));
        }

        log.info("list.size : {}", list.size());
        return list;
    }


}
