package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.market.ItemPrice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


// 이미지 관련
// https://wiki.albiononline.com/wiki/API:Render_service

// 데이터 관련
// https://www.albion-online-data.com/#i-want-all-the-data-from-the-api

// ?
// https://openalbion.com/categories.html

// 참고
// https://albion-profit-calculator.com/

@Service
public class MarketService{

    // 워리어
    // 헌터
    // 용병 후드 : T4_HEAD_LEATHER_SET1.png
    // 용병 재킷 : T4_ARMOR_LEATHER_SET1.png 1 ~ 3
    // ROYAL, MORGANA, HELL, UNDEAD, AVALON, FEY
    // 용병 신발 : T4_SHOES_LEATHER_SET1.png
    // 메이지

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String EAST = "https://east.albion-online-data.com/api/v2/stats/prices/";
    private final String WEST = "https://west.albion-online-data.com/api/v2/stats/prices/";
    private final String EUROPE = "https://europe.albion-online-data.com/api/v2/stats/prices/";

    private final String[]CITIES = {"martlock", "thetford", "bridgewatch", "fortsterling", "lymhurst", "caerleon", "brecilian"};



    private String getServerUrl(String server) {
        switch (server) {
            case "east" : return EAST;
            case "west" : return WEST;
            case "europe" : return EUROPE;
        }
        return EAST;
    }

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

    // https://west.albion-online-data.com/api/v2/stats/prices/T4_BAG,T5_BAG?locations=Caerleon,Bridgewatch&qualities=2
    public List<ItemPrice> getPrice(String server, String quality, String tier, String dotTier, String itemName) throws JsonProcessingException {
        String url = getServerUrl(server);
        StringBuilder requestUrl = new StringBuilder(url);
        requestUrl.append(tier).append("_").append(itemName).append(dotTier).append("?");
        requestUrl.append("lcations=").append(CITIES[0]);
        for (int i = 1; i < CITIES.length; i++) {
            requestUrl.append(",").append(CITIES[i]);
        }
        requestUrl.append("&qualities=").append(quality);

        log.info("request String : {}", requestUrl);


        String response = getResponse(requestUrl.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        List<ItemPrice> list = new ArrayList<>();
        for (JsonNode node : rootNode) {
             ItemPrice itemPrice = objectMapper.treeToValue(node, ItemPrice.class);
             list.add(itemPrice);
        }
        return list;
    }
}
