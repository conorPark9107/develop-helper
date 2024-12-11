package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.killboard.DeathBoard;
import com.albionhelper.helper.domain.killboard.KillBoard;
import com.albionhelper.helper.domain.Player;
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

@Service
public class KillboardService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String EAST = "https://gameinfo-sgp.albiononline.com/api/gameinfo";
    private final String WEST = "https://gameinfo.albiononline.com/api/gameinfo";
    private final String EUROPE = "https://gameinfo-ams.albiononline.com/api/gameinfo";

    // search?q={id}
    private final String GET_ID_URL = "/search?q=";

    // 킬
    private final String GET_KILLBOARD = "/players/<ID>/kills";

    // 데스
    private final String GET_DEATHBOARD = "/players/<ID>/deaths";;

    // 플레이어 정보
    private final String GET_PLAYERINFO = "/players/<ID>";

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
                .block();
//                .block(Duration.ofMillis(10_000));

    }

    // https://gameinfo-sgp.albiononline.com/api/gameinfo/search?q=Metzzi
    public List<Player> getPlayersInfo(String id, String location) throws JsonProcessingException {
        ArrayList<Player> list = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        location = getLocation(location);

        stringBuilder.append(location).append(GET_ID_URL).append(id);

        log.info("request url is {}", stringBuilder.toString());

        String response = getResponse(stringBuilder.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode playersNode = rootNode.get("players");

        for (JsonNode node : playersNode) {
            Player p = objectMapper.treeToValue(node, Player.class);
            list.add(p);
        }


        return list;
    }

    private String getLocation(String location) {
        switch (location) {
            case "east" : return EAST;
            case "west" : return WEST;
            case "europe" : return EUROPE;
        }
        return EAST;
    }


    // https://gameinfo-sgp.albiononline.com/api/gameinfo/players/PE5yINJmTSKfHUz05tLrbA/kills
    // https://gameinfo-sgp.albiononline.com/api/gameinfo/players/PE5yINJmTSKfHUz05tLrbA/death
    public List<KillBoard> getKillBoard(String id, String location) throws JsonProcessingException {
        location = getLocation(location);
        StringBuilder killBuilder = new StringBuilder(location);
        String killUrl = killBuilder.append(GET_KILLBOARD).toString().replace("<ID>", id);
        log.info("request url is {}", killUrl);
        String responseKillData = getResponse(killUrl);
        return getKillLog(responseKillData);
    }

    public List<DeathBoard> getDeathBoard(String id, String location) throws JsonProcessingException {
        location = getLocation(location);
        StringBuilder deathBuilder = new StringBuilder(location);
        String deathUrl = deathBuilder.append(GET_DEATHBOARD).toString().replace("<ID>", id);
        log.info("request url is {}", deathUrl);
        String responseDeathData = getResponse(deathUrl);
        return getDeathLog(responseDeathData);
    }


    private List<DeathBoard> getDeathLog(String responseDeathData) throws JsonProcessingException{
        List<DeathBoard> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseDeathData);

        for (JsonNode node : rootNode) {
            DeathBoard board = objectMapper.treeToValue(node, DeathBoard.class);
            list.add(board);
        }

        return list;
    }

    private List<KillBoard> getKillLog(String responseKillData) throws JsonProcessingException {
        List<KillBoard> list = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseKillData);

        for (JsonNode node : rootNode) {
            KillBoard board = objectMapper.treeToValue(node, KillBoard.class);
            list.add(board);
        }

        return list;
    }
}
