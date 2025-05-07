package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.killboard.PlayerLog;
import com.albionhelper.helper.domain.market.ItemPrice;
import com.albionhelper.helper.domain.market.MarketRank;
import com.albionhelper.helper.domain.market.MarketRankDTO;
import com.albionhelper.helper.enums.MarketServerRegion;
import com.albionhelper.helper.repository.MarketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String[]CITIES = {"martlock", "thetford", "bridgewatch", "fortsterling", "lymhurst", "caerleon", "brecilian"};

    private final MarketRepository repository;

    public MarketService(MarketRepository repository) {
        this.repository = repository;
    }


    private String getServerUrl(String server) {
        return MarketServerRegion.from(server).getUrl();
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
    public List<ItemPrice> getPriceOld(String server, String quality, String tier, String dotTier, String itemName) throws JsonProcessingException {
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

    public List<List<ItemPrice>> getResourcePrice(String server, String city, String[] beforeArr, String[] afterArr) throws JsonProcessingException {
        StringBuilder requestUrl = new StringBuilder(getServerUrl(server));

        StringBuilder beforeURL = getResourceURL(requestUrl, beforeArr, city);
        StringBuilder afterURL = getResourceURL(requestUrl, afterArr, city);

        log.info("request Resource Price URL(before) : {}", beforeURL);
        log.info("request Resource Price URL(afterfore) : {}", afterURL);

        String beforeResponse = getResponse(beforeURL.toString());
        String afterResponse = getResponse(afterURL.toString());

        List<List<ItemPrice>> list = new ArrayList<>();
        list.add(getResourceList(beforeResponse));
        list.add(getResourceList(afterResponse));

        return list;
    }

    private List<ItemPrice> getResourceList(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        List<ItemPrice> list = new ArrayList<>();
        for (JsonNode node : rootNode) {
            ItemPrice itemPrice = objectMapper.treeToValue(node, ItemPrice.class);
            list.add(itemPrice);
        }
        return list;
    }

    private StringBuilder getResourceURL(StringBuilder requestUrl, String[] array, String city) {
        StringBuilder builder = new StringBuilder(requestUrl);
        builder.append(array[0]);
        for(int i = 1; i < array.length; i++){
            builder.append(",").append(array[i]);
        }
        builder.append("?locations=").append(city);
        builder.append("&qualities=1");
        return builder;
    }

    public List<ItemPrice> getPrice(String server, String itemName) throws JsonProcessingException {
        StringBuilder urlBuilder = new StringBuilder(getServerUrl(server));
        urlBuilder.append(itemName);
        List<ItemPrice> list = new ArrayList<>();

        log.info("request String : {}", urlBuilder);
        String response = getResponse(urlBuilder.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        for (JsonNode node : rootNode) {
            ItemPrice itemPrice = objectMapper.treeToValue(node, ItemPrice.class);
            list.add(itemPrice);
        }

        return list;
    }

    @Transactional
    public void addCount(MarketRankDTO dto) {
        Optional<MarketRank> op = repository.findByItemId(dto.getItemId());
        if(op.isPresent()){
            MarketRank marketRank = op.get();
            marketRank.setCount(marketRank.getCount() + 1);
        }else{
            repository.save(dto.toEntity());
        }
    }

    public List<MarketRankDTO> getCounts() {
        return repository.findAllTop20(PageRequest.of(0, 20))
                .stream()
                .map(MarketRank::toDto)
                .collect(Collectors.toList());
    }
}
