package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.gold.Gold;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class GoldService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String EAST = "https://east.albion-online-data.com/api/v2/stats/gold?";
    private final String WEST = "https://west.albion-online-data.com/api/v2/stats/gold?";
    private final String EUROPE = "https://europe.albion-online-data.com/api/v2/stats//gold?";

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
        LocalDateTime startDate = endDate.minusDays(7);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = EAST + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request EAST : {}", url);
        String response = getResponse(url);
        return getPriceList(response, startDate);
    }

    public List<Gold> getGoldPriciesOnWest() throws JsonProcessingException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = WEST + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request WEST : {}", url);
        String response = getResponse(url);
        return getPriceList(response, startDate);
    }

    public List<Gold> getGoldPriciesOnEurope() throws JsonProcessingException {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-YYYY");

        String url = EUROPE + "date=" + formatters.format(startDate) + "&end_date=" + formatters.format(endDate);
        log.info("request EU : {}", url);
        String response = getResponse(url);
        return getPriceList(response, startDate);
    }

    private List<Gold> getPriceList(String response, LocalDateTime startDate) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        List<Gold> list = new ArrayList<>();
        int sum = 0;
        int count = 0;
        int day = startDate.getDayOfMonth();
        for (JsonNode node : rootNode) {
            Gold p = objectMapper.treeToValue(node, Gold.class);
            if(p.getTimeStemp().getDayOfMonth() == day){
                sum += p.getPrice();
                count++;
            }else{
                list.add(new Gold(sum/count, p.getTimeStemp().minusDays(1)));
                sum = p.getPrice();
                count = 0;

                startDate = startDate.plusDays(1);
                day = startDate.getDayOfMonth();
            }
        }
        return list;
    }


}
