package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.battle.Alliance;
import com.albionhelper.helper.domain.battle.Battle;
import com.albionhelper.helper.domain.battle.Guild;
import com.albionhelper.helper.domain.battle.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class BattlesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String EAST = "https://gameinfo-sgp.albiononline.com/api/gameinfo";
    private final String WEST = "https://gameinfo.albiononline.com/api/gameinfo";
    private final String EUROPE = "https://gameinfo-ams.albiononline.com/api/gameinfo";

    private final String GET_ID_URL = "/search?q=";
    private final String DEFAULT_BATTLES_URL = "/battles?&offset=0&limit=51&sort=recent";


    private String getLocation(String location) {
        switch (location) {
            case "east" : return EAST;
            case "west" : return WEST;
            case "europe" : return EUROPE;
        }
        return EAST;
    }

    private String getResponse(String requestUrl) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(-1))
                .build();

        WebClient webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();

        return webClient.get()
                .uri(requestUrl)
                .header("x-test", "header")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<Battle> getBattleList(String url) throws JsonProcessingException {

        String requestUrl = getLocation(url) + DEFAULT_BATTLES_URL;
        String response = getResponse(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        List<Battle> list = new ArrayList<>();
        for(int i = 0; i < rootNode.size(); i++){
            JsonNode node = rootNode.get(i);
            list.add(getBattle(node));
        }

        log.info("url is {}", requestUrl);
        return list;
    }

    private Battle getBattle(JsonNode node) throws JsonProcessingException {
        Battle b = new Battle();
        ObjectMapper objMapper = new ObjectMapper();

        b.setId(String.valueOf(node.get("id")));
        b.setEndTime(String.valueOf(node.get("endTime")));
        b.setTotalFame(String.valueOf(node.get("totalFame")));
        b.setTotalKills(String.valueOf(node.get("totalKills")));

        List<Player> playerList = new ArrayList<>();
        for(JsonNode player : node.get("players")){
            playerList.add(objMapper.treeToValue(player, Player.class));
        }

        List<Guild> guildList = new ArrayList<>();
        for(JsonNode guild : node.get("guilds")){
            guildList.add(objMapper.treeToValue(guild, Guild.class));
        }

        List<Alliance> allianceList = new ArrayList<>();
        for(JsonNode alliance : node.get("alliances")){
            allianceList.add(objMapper.treeToValue(alliance, Alliance.class));
        }

        b.setPlayers(playerList);
        b.setGuilds(guildList);
        b.setAlliances(allianceList);
        return b;
    }


}
